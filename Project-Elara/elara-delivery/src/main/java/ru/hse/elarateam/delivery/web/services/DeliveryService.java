package ru.hse.elarateam.delivery.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;
import ru.hse.elarateam.delivery.dto.request.SelectShipmentMethodRequestDTO;
import ru.hse.elarateam.delivery.dto.request.ShipmentMethodsRequestDTO;
import ru.hse.elarateam.delivery.dto.response.ShipmentMethodsResponseDTO;
import ru.hse.elarateam.delivery.model.Address;
import ru.hse.elarateam.delivery.model.Order;
import ru.hse.elarateam.delivery.model.ShipmentDetails;
import ru.hse.elarateam.delivery.model.status.OrderStatus;
import ru.hse.elarateam.delivery.web.mappers.AddressesMapper;
import ru.hse.elarateam.delivery.web.mappers.ShipmentMethodsMapper;
import ru.hse.elarateam.delivery.web.repositories.AddressesRepository;
import ru.hse.elarateam.delivery.web.repositories.OrdersRepository;
import ru.hse.elarateam.delivery.web.repositories.ShipmentDetailsRepository;
import ru.hse.elarateam.delivery.web.repositories.ShipmentMethodsRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final ShipmentMethodsMapper shipmentMethodsMapper;
    private final OrdersRepository ordersRepository;
    private final AddressesRepository addressesRepository;
    private final AddressesMapper addressesMapper;
    private final ShipmentDetailsRepository shipmentDetailsRepository;
    private final SDEKFeignService sdekFeignService;
    private final ShipmentMethodsRepository shipmentMethodsRepository;

    @Value("${elara.from-address-id}")
    private UUID fromAddressId;

    @Transactional(rollbackFor = RuntimeException.class)
    public ShipmentMethodsResponseDTO getShipmentMethods(ShipmentMethodsRequestDTO shipmentMethodsRequestDTO) {
        var orderId = shipmentMethodsRequestDTO.getOrderId();

        var order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id" + orderId + " not found."));

        checkOrderStatus(order);

        var fromAddress = addressesRepository.findById(fromAddressId)
                .orElseThrow(() -> new IllegalArgumentException("From address not found. Check database initialization."));

        /**
         * expected to be either acquired from getSavedAddresses or new.
         * if address is new the id should be null
         * if id is not null, but incorrect - exception will be thrown
         */
        var toAddressInfoDTO = shipmentMethodsRequestDTO.getToAddress();
        var toAddress = new Address();

        if (toAddressInfoDTO.getId() != null) {
            toAddress = addressesRepository.findById(toAddressInfoDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("To address with id " + toAddressInfoDTO.getId() + " not found."));
        } else {
            var newAddress = addressesMapper.addressInfoDTOToAddress(toAddressInfoDTO);
            newAddress.setUserId(order.getUserId());
            toAddress = addressesRepository.saveAndFlush(newAddress);
            log.info("New address saved: " + toAddress);
        }

        /**
         * saving shipment details to order
         */
        var shipmentDetails = ShipmentDetails.builder().fromAddress(fromAddress).toAddress(toAddress).build();
        shipmentDetails = shipmentDetailsRepository.saveAndFlush(shipmentDetails);
        order.setShipmentDetails(shipmentDetails);
        order = ordersRepository.saveAndFlush(order);
        log.info("Assigned shipment details " + shipmentDetails + " to order " + order);

        var shipmentMethods = sdekFeignService.getShipmentMethods(
                order.getTotalHeight(),
                order.getTotalWidth(),
                order.getTotalLength(),
                order.getTotalWeight(),
                addressesMapper.addressToAddressInfoDTO(fromAddress),
                addressesMapper.addressToAddressInfoDTO(toAddress));

        var shipmentMethodsDTOs = shipmentMethods.stream()
                .map(shipmentMethodsMapper::shipmentMethodToShipmentMethodInfoDTO)
                .toList();
        log.debug("Returning shipment methods: " + shipmentMethodsDTOs);

        return ShipmentMethodsResponseDTO.builder().shipmentMethods(shipmentMethodsDTOs).build();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void selectShipmentMethod(SelectShipmentMethodRequestDTO selectShipmentMethodRequestDTO) {
        var orderId = selectShipmentMethodRequestDTO.getOrderId();

        var order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id" + orderId + " not found"));

        checkOrderStatus(order);

        var shipmentDetails = order.getShipmentDetails();

        var shipmentMethod = shipmentMethodsMapper.shipmentMethodInfoDTOToShipmentMethod(
                selectShipmentMethodRequestDTO.getShipmentMethod());
        log.debug("trying to save shipment method: " + shipmentMethod);
        shipmentMethod = shipmentMethodsRepository.saveAndFlush(shipmentMethod);
        log.info("Saved shipment method: " + shipmentMethod);

        shipmentDetails.setShipmentMethod(shipmentMethod);
        shipmentDetails.setDeliveryCost(shipmentMethod.getDeliverySum());
        log.debug("trying to save shipment details: " + shipmentDetails);
        shipmentDetailsRepository.saveAndFlush(shipmentDetails);
        log.info("Saved shipment details: " + shipmentDetails);

        order.setStatus(OrderStatus.DELIVERY_METHOD_SELECTED);

        order = ordersRepository.saveAndFlush(order);
        log.debug("Order " + order + " status changed to " + order.getStatus());
    }

    @Transactional(readOnly = true)
    public List<AddressInfoDTO> getSavedAddresses(UUID userId) {
        var addresses = addressesRepository.findByUserId(userId);
        log.info("Addresses: " + addresses + "found by user id: " + userId);
        return addresses.stream().map(addressesMapper::addressToAddressInfoDTO).toList();
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            log.error("Order " + order.getId() + " is already paid.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already paid.");
        }
        if (order.getStatus() == OrderStatus.PACKED) {
            log.error("Order " + order.getId() + " is already packed.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already packed.");
        }
        if (order.getStatus() == OrderStatus.IN_DELIVERY) {
            log.error("Order " + order.getId() + " is already in delivery.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already in delivery.");
        }
        if (order.getStatus() == OrderStatus.DELIVERED) {
            log.error("Order " + order.getId() + " is already delivered.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already delivered.");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.error("Order " + order.getId() + " is cancelled.");
            throw new IllegalArgumentException("Order " + order.getId() + " is cancelled.");
        }

    }
}
