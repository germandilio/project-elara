package ru.hse.elarateam.users.web.services;

import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;

import java.util.UUID;

public interface UsersService {

    void saveUser(UserRegisterRequestDTO userRegisterRequest);

    UserProfileDTO updateUserProfile(UserProfileUpdateRequestDTO userProfileUpdateRequest);

    UserProfileDTO getUserProfileById(UUID userId);

    UserInfoDTO getUserInfoById(UUID userId);

    void deleteUserById(UUID userId);

    void changePassword(ChangePasswordRequestDTO changePasswordRequest);

    UserInfoDTO getUserInfoByResetPasswordToken(String token);

    void verifyEmail(String verificationToken);

    /**
     * Checks if login is available
     * @param login login to check
     * @return true if login is available, false otherwise
     */
    boolean checkLoginAvailability(String login);

    void resetPassword(String email);
}
