package ru.hse.elarateam.orders.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.orders.dto.info.AddressInfoDTO;
import ru.hse.elarateam.orders.model.Address;
@Mapper
public interface AddressesMapper {
    Address addressInfoDTOToAddress(AddressInfoDTO addressDTO);

    AddressInfoDTO addressToAddressInfoDTO(Address address);
}
