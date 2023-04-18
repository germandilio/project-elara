package ru.hse.elarateam.delivery.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.delivery.dto.info.ShipmentMethodInfoDTO;
import ru.hse.elarateam.delivery.dto.request.ShipmentMethodsRequestDTO;
import ru.hse.elarateam.delivery.web.feign.SDEKFeignClient;
import ru.hse.elarateam.delivery.web.repositories.AddressesRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final AddressesRepository addressesRepository;
    private final SDEKFeignService sdekFeignService;

    @Value("${elara.from-address-id}")
    private UUID fromAddressId;
    // todo add logs

    public List<ShipmentMethodInfoDTO> getShipmentMethods(ShipmentMethodsRequestDTO shipmentMethodsRequestDTO) {
        var orderId = shipmentMethodsRequestDTO.getOrderId();
        var toAddress = shipmentMethodsRequestDTO.getToAddress();
        var fromAddress = addressesRepository.findById(fromAddressId)
                .orElseThrow(() -> new RuntimeException("From address not found. Check database initialization."));


        var shipmentMethods = generateShipmentMethods(fromAddress, toAddress);
    }
}
