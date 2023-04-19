package ru.hse.elarateam.delivery.dto.info;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ShipmentDetailsInfoDTO {
    private UUID id;
    private AddressInfoDTO fromAddress;
    private AddressInfoDTO toAddress;
    private BigDecimal deliveryCost;
    private ShipmentMethodInfoDTO shipmentMethod;
}
