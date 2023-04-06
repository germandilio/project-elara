package ru.hse.elarateam.web.services.validation;

import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;

public interface UserValidator {
    void validateUserRegisterRequest(UserRegisterRequestDTO userRegisterRequest);
    void validateUserProfileUpdateRequest(UserProfileUpdateRequestDTO userProfileUpdateRequest);
    void validateChangePasswordRequest(ChangePasswordRequestDTO changePasswordRequest);
}
