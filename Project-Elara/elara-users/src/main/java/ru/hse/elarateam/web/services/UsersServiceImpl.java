package ru.hse.elarateam.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.dto.UserInfoDTO;
import ru.hse.elarateam.dto.UserProfileDTO;
import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.web.services.email.EmailService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersServiceImpl implements UsersService {

    @Value("${token-factory.token.expiration-time.hours}")
    private int passwordTokenExpirationTimeHours = 24;

    private final EmailService emailService;

    private final UsersDBService usersDBService;

    private final TokenGenerator tokenGenerator;

    @Override
    public void saveUser(UserRegisterRequestDTO userRegisterRequest) {
        final var user = usersDBService.saveUser(userRegisterRequest);

        final String token = tokenGenerator.generate();
        emailService.sendEmailVerification(user.getUserId(), token);
    }

    @Override
    public UserProfileDTO updateUserProfile(UserProfileUpdateRequestDTO userProfileUpdateRequest) {
        final var userBeforeUpdate = usersDBService.getUserInfoById(userProfileUpdateRequest.getUserId());
        final var userOldEmail = userBeforeUpdate.getEmail();

        final var userProfile = usersDBService.updateUserProfile(userProfileUpdateRequest);

        if (!userProfile.getEmail().equals(userOldEmail)) {
            log.debug("Generate token for user with id {} and send email verification", userBeforeUpdate.getUserId());
            final String token = tokenGenerator.generate();
            log.trace("Generated token: {}", token);
            emailService.sendEmailVerification(userBeforeUpdate.getUserId(), token);
        }

        return userProfile;
    }

    @Override
    public UserProfileDTO getUserProfileById(UUID userId) {
        return usersDBService.getUserProfileById(userId);
    }

    @Override
    public UserInfoDTO getUserInfoById(UUID userId) {
        return usersDBService.getUserInfoById(userId);
    }

    @Override
    public void deleteUserById(UUID userId) {
        usersDBService.deleteUserById(userId);
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequest) {
        usersDBService.changePassword(changePasswordRequest);
    }

    @Override
    public UserInfoDTO getUserInfoByResetPasswordToken(String token) {
        return usersDBService.getUserInfoByResetPasswordToken(token);
    }

    @Override
    public void verifyEmail(String verificationToken) {
        usersDBService.verifyEmail(verificationToken);
    }

    @Override
    public boolean checkLoginAvailability(String login) {
        return usersDBService.checkLoginAvailability(login);
    }

    @Override
    public void resetPassword(String email) {
        log.debug("Generate token for user with email {} and send reset password email", email);
        final String token = tokenGenerator.generate();
        log.trace("Generated token: {}", token);
        final var user = usersDBService.saveResetPasswordToken(token, passwordTokenExpirationTimeHours, email);

        emailService.sendResetPasswordEmail(user.getUserId(), token);
    }
}
