package ru.hse.elarateam.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.model.UserProfile;
import ru.hse.elarateam.model.UserServiceInfo;

@Mapper
public interface UserMapper {
    @Mapping(source = "login", target = "email")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userServiceInfo", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "lastUpdateTime", ignore = true)
    UserProfile userRegisterRequestDTOtoUserProfile(UserRegisterRequestDTO userRegisterRequestDTO);
}
