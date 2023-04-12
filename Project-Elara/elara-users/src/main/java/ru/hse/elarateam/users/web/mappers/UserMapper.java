package ru.hse.elarateam.users.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.users.dto.UserDTO;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.model.UserProfile;
import ru.hse.elarateam.users.model.UserServiceInfo;

@Mapper
public interface UserMapper {
    @Mapping(source = "login", target = "email")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userServiceInfo", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "lastUpdateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    UserProfile userRegisterRequestDTOtoUserProfile(UserRegisterRequestDTO userRegisterRequestDTO);

    UserProfileDTO userProfileToUserProfileDTO(UserProfile userProfile);

    @Mapping(source = "login", target = "email")
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "userProfile.firstName", target = "firstName")
    UserInfoDTO userServiceInfoToUserInfoDTO(UserServiceInfo userServiceInfo);

    @Mapping(target = "role", source = "userServiceInfo.role.role")
    UserDTO userProfileToUserDTO(UserProfile userProfile);

}
