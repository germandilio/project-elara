package ru.hse.elarateam.delivery.dto.request;


import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.delivery.dto.info.ShipmentMethodInfoDTO;

import java.util.UUID;

@Data
@Builder
public class SelectShipmentMethodRequestDTO {
    private UUID orderId;
    private ShipmentMethodInfoDTO shipmentMethod;
}
