package ru.hse.elarateam.delivery.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;
import ru.hse.elarateam.delivery.model.Address;

import java.util.Set;

@Mapper
public interface AddressesMapper {
    AddressInfoDTO addressToAddressInfoDTO(Address address);

    @Mapping(target = "id", ignore = true)
    Address addressInfoDTOToAddress(AddressInfoDTO addressInfoDTO);

//    Set<Address> map(Set<AddressInfoDTO> addressInfoDTOs);
}
