package ru.hse.elarateam.delivery.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.delivery.dto.info.ShipmentMethodInfoDTO;
import ru.hse.elarateam.delivery.model.ShipmentMethod;

@Mapper
public interface ShipmentMethodsMapper {
    ShipmentMethodInfoDTO shipmentMethodToShipmentMethodInfoDTO(ShipmentMethod shipmentMethod);

    @Mapping(target = "id", ignore = true)
    ShipmentMethod shipmentMethodInfoDTOToShipmentMethod(ShipmentMethodInfoDTO shipmentMethodInfoDTO);
}
