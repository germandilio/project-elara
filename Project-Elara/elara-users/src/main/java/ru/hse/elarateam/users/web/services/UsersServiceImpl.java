package ru.hse.elarateam.users.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.web.services.email.EmailService;

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
    public UserInfoDTO saveUser(UserRegisterRequestDTO userRegisterRequest) {
        final var user = usersDBService.saveUser(userRegisterRequest);

        final String token = tokenGenerator.generate();
        emailService.sendEmailVerification(user.getUserId(), token);
        return user;
    }

    @Override
    public void updateUserProfile(UserProfileUpdateRequestDTO userProfileUpdateRequest) {
        final var userBeforeUpdate = usersDBService.getUserInfoById(userProfileUpdateRequest.getUserId());
        final var userOldEmail = userBeforeUpdate.getEmail();

        final var userProfile = usersDBService.updateUserProfile(userProfileUpdateRequest);

        if (!userProfile.getEmail().equals(userOldEmail)) {
            log.debug("Generate token for user with id {} and send email verification", userBeforeUpdate.getUserId());
            final String token = tokenGenerator.generate();
            log.trace("Generated token: {}", token);
            emailService.sendEmailVerification(userBeforeUpdate.getUserId(), token);
        }
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
    public void verifyEmail(String verificationToken) {
        usersDBService.verifyEmail(verificationToken);
    }

    @Override
    public boolean checkLoginAvailability(String login) {
        return usersDBService.checkLoginAvailability(login);
    }

    @Override
    public void forgotPassword(String login) {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login cannot be null or empty");
        }

        log.debug("Generate token for user with login {} and send reset password login", login);
        final String token = tokenGenerator.generate();
        log.trace("Generated token: {}", token);
        final var user = usersDBService.saveResetPasswordToken(token, passwordTokenExpirationTimeHours, login);

        emailService.sendResetPasswordEmail(user.getUserId(), token);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequest) {
        log.debug("Reset password for user with token {}", resetPasswordRequest.getResetPasswordToken());
        usersDBService.resetPassword(resetPasswordRequest);
    }
}
