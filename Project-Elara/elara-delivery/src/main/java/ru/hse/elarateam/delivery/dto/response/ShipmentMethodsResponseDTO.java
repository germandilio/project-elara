package ru.hse.elarateam.delivery.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.delivery.dto.info.ShipmentMethodInfoDTO;

import java.util.List;

@Data
@Builder
public class ShipmentMethodsResponseDTO {
    private List<ShipmentMethodInfoDTO> shipmentMethods;
}
