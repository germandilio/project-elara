package ru.hse.elarateam.orders.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.info.ShipmentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;
import ru.hse.elarateam.orders.mappers.OrderedItemsMapper;
import ru.hse.elarateam.orders.mappers.OrdersMapper;
import ru.hse.elarateam.orders.mappers.PaymentDetailsMapper;
import ru.hse.elarateam.orders.mappers.ShipmentDetailsMapper;
import ru.hse.elarateam.orders.model.status.OrderStatus;
import ru.hse.elarateam.orders.repositories.OrdersRepository;
import ru.hse.elarateam.orders.repositories.PaymentDetailsRepository;
import ru.hse.elarateam.orders.repositories.ShipmentDetailsRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final ShipmentDetailsRepository shipmentDetailsRepository;
    private final ShipmentDetailsMapper shipmentDetailsMapper;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final PaymentDetailsMapper paymentDetailsMapper;
    private final OrderedItemsMapper orderedItemsMapper;
    private final ProductsServiceFeign productsServiceFeign;

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

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO changeShipmentDetails(ShipmentDetailsInfoDTO shipmentDetailsInfoDTO, UUID orderId) {
        if (!ordersRepository.existsById(orderId)) {
            log.error("Order with id " + orderId + " not found.");
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        var order = ordersRepository.findById(orderId).get();
        // todo подумать, возможно стоит передавать paymentDetailsId и забирать объект из базы
        if (!shipmentDetailsRepository.existsById(shipmentDetailsInfoDTO.getId())) {
            log.error("Shipment details object with id " + shipmentDetailsInfoDTO.getId() + " not found.");
            throw new IllegalArgumentException("Shipment details object with id " + shipmentDetailsInfoDTO.getId() + " not found.");
        }
        order.setShipmentDetails(shipmentDetailsMapper.shipmentDetailsInfoDTOToShipmentDetails(shipmentDetailsInfoDTO));
        var saved = ordersRepository.save(order);
        log.info("Shipment details for order " + orderId + " changed for: " + saved.getShipmentDetails());
        return ordersMapper.orderToOrderResponseDTO(saved);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO changePaymentDetails(PaymentDetailsInfoDTO paymentDetailsInfoDTO, UUID orderId) {
        if (!ordersRepository.existsById(orderId)) {
            log.error("Order with id " + orderId + " not found.");
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        var order = ordersRepository.findById(orderId).get();
        // todo подумать, возможно стоит передавать paymentDetailsId и забирать объект из базы
        if (!paymentDetailsRepository.existsById(paymentDetailsInfoDTO.getId())) {
            log.error("Payment details object with id " + paymentDetailsInfoDTO.getId() + " not found.");
            throw new IllegalArgumentException("Payment details object with id " + paymentDetailsInfoDTO.getId() + " not found.");
        }
        order.setPaymentDetails(paymentDetailsMapper.paymentDetailsInfoDTOToPaymentDetails(paymentDetailsInfoDTO));
        var saved = ordersRepository.save(order);
        log.info("Payment details for order " + orderId + " changed for: " + saved.getPaymentDetails());
        return ordersMapper.orderToOrderResponseDTO(saved);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO changeOrderStatus(UUID orderId, OrderStatus status) {
        if (!ordersRepository.existsById(orderId)) {
            log.error("Order with id " + orderId + " not found.");
            throw new IllegalArgumentException("Order with id " + orderId + " not found.");
        }
        var order = ordersRepository.findById(orderId).get();

        if(status == OrderStatus.CANCELLED){
            var deallocationRequest = OrderRequestDTO.builder()
                    .userId(order.getUserId())
                    .positions(order.getOrderedItems().stream()
                            .map(orderedItemsMapper::orderedItemToOrderedItemRequestDTO)
                            .toList())
                    .build();

            productsServiceFeign.deallocateProducts(deallocationRequest);
            log.info("Products deallocated for order: " + orderId);
        }

        order.setStatus(status);
        var saved = ordersRepository.save(order);
        log.info("Status for order " + orderId + " changed for: " + saved.getStatus());
        return ordersMapper.orderToOrderResponseDTO(saved);

        // todo sent user email about status change
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO) {


        var saved = ordersRepository.save(order);
        log.info("Order placed: " + saved);
        return ordersMapper.orderToOrderResponseDTO(saved);
    }

}
