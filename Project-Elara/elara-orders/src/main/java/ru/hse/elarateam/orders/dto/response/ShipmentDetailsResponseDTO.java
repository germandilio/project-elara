package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.orders.dto.info.AddressInfoDTO;
import ru.hse.elarateam.orders.dto.info.ShipmentMethodInfoDTO;

import java.math.BigDecimal;

@Data
@Builder
public class ShipmentDetailsResponseDTO {
    private AddressInfoDTO fromAddress;
    private AddressInfoDTO toAddress;
    private BigDecimal deliveryCost;
    private ShipmentMethodInfoDTO shipmentMethod;
}
