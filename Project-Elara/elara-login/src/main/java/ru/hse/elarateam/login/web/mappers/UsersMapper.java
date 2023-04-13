package ru.hse.elarateam.login.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.login.dto.UserProfileDTO;
import ru.hse.elarateam.login.dto.UserServiceInfoDTO;
import ru.hse.elarateam.login.model.UserProfile;
import ru.hse.elarateam.login.model.UserServiceInfo;

@Mapper
public interface UsersMapper {
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "role.id", target = "roleId")
    @Mapping(source = "role.role", target = "roleName")
    UserServiceInfoDTO userServiceInfoToUserServiceInfoDTO(UserServiceInfo userServiceInfo);

    UserProfileDTO userProfileToUserProfileDTO(UserProfile userProfile);
}
