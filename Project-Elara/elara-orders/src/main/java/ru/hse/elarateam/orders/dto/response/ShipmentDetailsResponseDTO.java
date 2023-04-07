package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShipmentDetailsResponseDTO {
    private AddressResponseDTO fromAddress;
    private AddressResponseDTO toAddress;
    private BigDecimal deliveryCost;
    private ShipmentMethodResponseDTO shipmentMethod;
}
