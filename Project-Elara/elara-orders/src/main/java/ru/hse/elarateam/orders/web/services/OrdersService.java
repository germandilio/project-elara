package ru.hse.elarateam.orders.web.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;
import ru.hse.elarateam.orders.model.Order;
import ru.hse.elarateam.orders.model.OrderedItem;
import ru.hse.elarateam.orders.model.status.OrderStatus;
import ru.hse.elarateam.orders.web.mappers.OrderedItemsMapper;
import ru.hse.elarateam.orders.web.mappers.OrdersMapper;
import ru.hse.elarateam.orders.web.repositories.OrderedItemsRepository;
import ru.hse.elarateam.orders.web.repositories.OrdersRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    //    private final ShipmentDetailsRepository shipmentDetailsRepository;
//    private final ShipmentDetailsMapper shipmentDetailsMapper;
//    private final PaymentDetailsRepository paymentDetailsRepository;
//    private final PaymentDetailsMapper paymentDetailsMapper;
    private final OrderedItemsRepository orderedItemsRepository;
    private final OrderedItemsMapper orderedItemsMapper;
    private final ProductsServiceFeign productsServiceFeign;
    private final BinPackingSolver3d binPackingSolver3d;

    // todo logs
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(UUID orderId) {
        return ordersMapper.orderToOrderResponseDTO(
                ordersRepository.findById(orderId).orElse(null));
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getOrdersByUserId(UUID userId, Pageable pageable) {
        return ordersRepository.findAllByUserId(userId, pageable)
                .map(ordersMapper::orderToOrderResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDTO> getAllOrders(Pageable pageable) {
        return ordersRepository.findAll(pageable)
                .map(ordersMapper::orderToOrderResponseDTO);
    }

//    @Transactional(rollbackFor = RuntimeException.class)
//    public OrderResponseDTO changeShipmentDetails(ShipmentDetailsInfoDTO shipmentDetailsInfoDTO, UUID orderId) {
//        if (!ordersRepository.existsById(orderId)) {
//            log.error("Order with id " + orderId + " not found.");
//            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
//        }
//        var order = ordersRepository.findById(orderId).get();
//        // todo подумать, возможно стоит передавать paymentDetailsId и забирать объект из базы
//        if (!shipmentDetailsRepository.existsById(shipmentDetailsInfoDTO.getId())) {
//            log.error("Shipment details object with id " + shipmentDetailsInfoDTO.getId() + " not found.");
//            throw new IllegalArgumentException("Shipment details object with id " + shipmentDetailsInfoDTO.getId() + " not found.");
//        }
//        order.setShipmentDetails(shipmentDetailsMapper.shipmentDetailsInfoDTOToShipmentDetails(shipmentDetailsInfoDTO));
//        var saved = ordersRepository.save(order);
//        log.info("Shipment details for order " + orderId + " changed for: " + saved.getShipmentDetails());
//        return ordersMapper.orderToOrderResponseDTO(saved);
//    }
//
//    @Transactional(rollbackFor = RuntimeException.class)
//    public OrderResponseDTO changePaymentDetails(PaymentDetailsInfoDTO paymentDetailsInfoDTO, UUID orderId) {
//        if (!ordersRepository.existsById(orderId)) {
//            log.error("Order with id " + orderId + " not found.");
//            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
//        }
//        var order = ordersRepository.findById(orderId).get();
//        // todo подумать, возможно стоит передавать paymentDetailsId и забирать объект из базы
//        if (!paymentDetailsRepository.existsById(paymentDetailsInfoDTO.getId())) {
//            log.error("Payment details object with id " + paymentDetailsInfoDTO.getId() + " not found.");
//            throw new IllegalArgumentException("Payment details object with id " + paymentDetailsInfoDTO.getId() + " not found.");
//        }
//        order.setPaymentDetails(paymentDetailsMapper.paymentDetailsInfoDTOToPaymentDetails(paymentDetailsInfoDTO));
//        var saved = ordersRepository.save(order);
//        log.info("Payment details for order " + orderId + " changed for: " + saved.getPaymentDetails());
//        return ordersMapper.orderToOrderResponseDTO(saved);
//    }

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO changeOrderStatus(UUID orderId, OrderStatus status) {
        if (!ordersRepository.existsById(orderId)) {
            log.error("Order with id " + orderId + " not found.");
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        var order = ordersRepository.findById(orderId).get();

        if (status == OrderStatus.CANCELLED) {
            var deallocationRequest = OrderRequestDTO.builder()
                    .userId(order.getUserId())
                    .positions(order.getOrderedItems().stream()
                            .map(orderedItemsMapper::orderedItemToOrderedItemRequestDTO)
                            .toList())
                    .build();

            var deallocatedIds = Objects.requireNonNull(productsServiceFeign.deallocateProducts(deallocationRequest).getBody());
            log.info("Products " + deallocatedIds + " deallocated for order: " + orderId);
        }

        order.setStatus(status);
        var saved = ordersRepository.save(order);
        log.info("Status for order " + orderId + " changed for: " + saved.getStatus());
        return ordersMapper.orderToOrderResponseDTO(saved);

        // todo sent user email about status change
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {
        // knocking to products service
        var allocatedProducts = productsServiceFeign.allocateProducts(orderRequestDTO).getBody();
        log.info("Products " + allocatedProducts + " allocated.");

        var orderedItems = new ArrayList<OrderedItem>();
        var totalWithDiscount = new BigDecimal(0);
        var totalWithoutDiscount = new BigDecimal(0);
        for (var item : Objects.requireNonNull(allocatedProducts)) {
            orderedItems.add(OrderedItem.builder()
                    .productId(item.getProductId())
                    .price(item.getPrice())
                    .discount(item.getDiscount())
                    .quantity(item.getQuantity())
                    .build());

            totalWithDiscount = totalWithDiscount.add(
                    item.getPrice()
                            .multiply(new BigDecimal(item.getQuantity()))
                            .multiply(new BigDecimal((100 - item.getDiscount()) / 100)));

            totalWithoutDiscount = totalWithoutDiscount.add(
                    item.getPrice()
                            .multiply(new BigDecimal(item.getQuantity())));
        }
        var savedItems = orderedItemsRepository.saveAllAndFlush(orderedItems);
        log.info("Ordered items saved: " + savedItems);

        var dimentions = binPackingSolver3d.solve(savedItems);
        log.info("Optimal dimentions. Height:{}, Width:{}, Length:{}, Weight: {}",
                dimentions.get(0), dimentions.get(1), dimentions.get(2), dimentions.get(3));


        var order = Order.builder()
                .userId(orderRequestDTO.getUserId())
                .orderedItems(new HashSet<>(savedItems))
                .totalWithDiscount(totalWithDiscount)
                .total(totalWithoutDiscount)
                .status(OrderStatus.ALLOCATED)
                .totalHeight(dimentions.get(0))
                .totalWidth(dimentions.get(1))
                .totalLength(dimentions.get(2))
                .totalWeight(dimentions.get(3))
                .build();

        var savedOrder = ordersRepository.save(order);
        log.info("Order placed: " + savedOrder);

        return ordersMapper.orderToOrderResponseDTO(savedOrder);
    }

}
