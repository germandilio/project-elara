package ru.hse.elarateam.users.web.services;

import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;

import java.util.UUID;

public interface UsersDBService {
    UserInfoDTO saveUser(final UserRegisterRequestDTO userRegisterRequest);

    UserProfileDTO updateUserProfile(final UserProfileUpdateRequestDTO userProfileUpdateRequest);

    UserProfileDTO getUserProfileById(final UUID userId);

    UserInfoDTO getUserInfoById(final UUID userId);

    void deleteUserById(final UUID userId);

    void verifyEmail(final String verificationToken);

    /**
     * Checks if login is available
     * @param login login to check
     * @return true if login is available, false otherwise
     */
    boolean checkLoginAvailability(final String login);

    UserInfoDTO saveResetPasswordToken(String token, int expirationTimeHours, String email);

    void changePassword(final ChangePasswordRequestDTO changePasswordRequest);

    void resetPassword(ResetPasswordRequestDTO resetPasswordRequest);
}
