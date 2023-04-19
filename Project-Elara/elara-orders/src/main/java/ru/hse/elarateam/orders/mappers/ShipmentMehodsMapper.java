package ru.hse.elarateam.orders.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.orders.dto.info.ShipmentMethodInfoDTO;
import ru.hse.elarateam.orders.model.ShipmentMethod;

@Mapper
public interface ShipmentMehodsMapper {
    ShipmentMethod shipmentMethodInfoDTOToShipmentMethod(ShipmentMethodInfoDTO shipmentMethodInfoDTO);

    ShipmentMethodInfoDTO shipmentMethodToShipmentMethodInfoDTO(ShipmentMethod shipmentMethod);
}
