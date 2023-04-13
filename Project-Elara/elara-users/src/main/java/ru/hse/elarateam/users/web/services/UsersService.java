package ru.hse.elarateam.users.web.services;

import ru.hse.elarateam.users.dto.UserDTO;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;

import java.util.UUID;

public interface UsersService {

    UserInfoDTO saveUser(UserRegisterRequestDTO userRegisterRequest);

    UserDTO updateUserProfile(UserProfileUpdateRequestDTO userProfileUpdateRequest);

    UserDTO getUserProfileById(UUID userId);

    UserInfoDTO getUserInfoById(UUID userId);

    void deleteUserById(UUID userId);

    void changePassword(ChangePasswordRequestDTO changePasswordRequest);

    void verifyEmail(String verificationToken);

    /**
     * Checks if login is available
     *
     * @param login login to check
     * @return true if login is available, false otherwise
     */
    boolean checkLoginAvailability(String login);

    void forgotPassword(String email);

    void resetPassword(ResetPasswordRequestDTO resetPasswordRequest);
}
