package ru.hse.elarateam.orders.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.orders.dto.info.ShipmentDetailsInfoDTO;
import ru.hse.elarateam.orders.model.ShipmentDetails;

@Mapper(uses = {AddressesMapper.class, ShipmentMehodsMapper.class})
public interface ShipmentDetailsMapper {
    ShipmentDetailsInfoDTO shipmentDetailsToShipmentDetailsInfoDTO(ShipmentDetails shipmentDetails);

    ShipmentDetails shipmentDetailsInfoDTOToShipmentDetails(ShipmentDetailsInfoDTO shipmentDetailsInfoDTO);
}
