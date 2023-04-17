package ru.hse.elarateam.users.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.users.dto.UserDTO;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.model.RoleEnum;
import ru.hse.elarateam.users.model.UserServiceInfo;
import ru.hse.elarateam.users.web.controllers.advice.ConflictException;
import ru.hse.elarateam.users.web.controllers.advice.UserNotFountException;
import ru.hse.elarateam.users.web.converters.PasswordConverter;
import ru.hse.elarateam.users.web.mappers.UserMapper;
import ru.hse.elarateam.users.web.repositories.RoleRepository;
import ru.hse.elarateam.users.web.repositories.UsersProfileRepository;
import ru.hse.elarateam.users.web.repositories.UsersServiceInfoRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserDBServiceImpl implements UsersDBService {

    private final UsersServiceInfoRepository usersServiceInfoRepository;

    private final UsersProfileRepository usersProfileRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordConverter passwordConverter;

    @Transactional
    @Override
    public UserInfoDTO saveUser(final UserRegisterRequestDTO userRegisterRequest) {
        // login should not be already assigned
        usersServiceInfoRepository.findByLogin(userRegisterRequest.getLogin())
                .ifPresent(user -> {
                    throw new ConflictException("User with login " + userRegisterRequest.getLogin() + " already exists");
                });

        final var initialRole = roleRepository.findOneByRole(RoleEnum.EMAIL_NOT_VERIFIED)
                .orElseThrow(() -> new IllegalStateException("Role " + RoleEnum.EMAIL_NOT_VERIFIED + " not found"));

        final var userProfile = userMapper.userRegisterRequestDTOtoUserProfile(userRegisterRequest);

        final var savedProfile = usersProfileRepository.saveAndFlush(userProfile);

        final var userServiceInfo = UserServiceInfo.builder()
                .id(savedProfile.getId())
                .userProfile(savedProfile)
                .login(userRegisterRequest.getLogin())
                .password(userRegisterRequest.getPassword())
                .role(initialRole)
                .build();

        log.trace("Saving user with login {}", userRegisterRequest.getLogin());
        log.trace("Set default role {} to user with login {}", initialRole.getRole(), userRegisterRequest.getLogin());

        final var persistentUser = usersServiceInfoRepository.saveAndFlush(userServiceInfo);

        return userMapper.userServiceInfoToUserInfoDTO(persistentUser);
    }

    @Transactional
    @Override
    public UserDTO updateUserProfile(final UserProfileUpdateRequestDTO userProfileUpdateRequest) {
        // check if user exists
        final var persistentUserInfo = usersServiceInfoRepository.findById(userProfileUpdateRequest.getUserId());
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with id " + userProfileUpdateRequest.getUserId() + " not found");
        }

        final var userProfile = persistentUserInfo.get().getUserProfile();
        if (userProfile == null) {
            throw new IllegalStateException("User profile not found");
        }

        // email was changed
        if (!persistentUserInfo.get().getLogin().equals(userProfileUpdateRequest.getEmail())) {
            updateEmail(persistentUserInfo, userProfileUpdateRequest.getEmail());
        }

        // update profile
        userProfile.setFirstName(userProfileUpdateRequest.getFirstName());
        userProfile.setLastName(userProfileUpdateRequest.getLastName());
        userProfile.setPictureUrl(userProfileUpdateRequest.getPictureUrl());
        userProfile.setBirthDate(userProfileUpdateRequest.getBirthDate());

        // save changes
        var updatedPersistentUserInfo = usersServiceInfoRepository.saveAndFlush(persistentUserInfo.get());
        log.trace("Updated user jwt info: {}", updatedPersistentUserInfo);
        log.trace("Updated user profile: {}", updatedPersistentUserInfo.getUserProfile());

        return userMapper.userProfileToUserDTO(updatedPersistentUserInfo.getUserProfile());
    }

    private void updateEmail(final Optional<UserServiceInfo> persistentUserInfo, final String newEmail) {
        persistentUserInfo.ifPresent(userInfo -> {
            var userProfile = userInfo.getUserProfile();

            log.trace("Email was changed from {} to {}", persistentUserInfo.get().getLogin(), newEmail);

            // check if new email is available
            usersServiceInfoRepository.findByLogin(newEmail)
                    .ifPresent(user -> {
                        throw new IllegalArgumentException("User with login " + newEmail + " already exists");
                    });
            // set new email to profile and jwt info
            persistentUserInfo.get().setLogin(newEmail);
            userProfile.setEmail(newEmail);

            // set new role to verify email again
            var newRole = roleRepository.findOneByRole(RoleEnum.EMAIL_NOT_VERIFIED)
                    .orElseThrow(() -> new IllegalStateException("Role " + RoleEnum.EMAIL_NOT_VERIFIED + " not found"));
            persistentUserInfo.get().setRole(newRole);

            log.trace("Set new role {} to user {}", newRole, persistentUserInfo.get().getLogin());
        });
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(final UUID userId) {
        final var persistentUserProfile = usersProfileRepository.findById(userId);
        if (persistentUserProfile.isEmpty()) {
            throw new UserNotFountException("User with id " + userId + " not found");
        }

        final var userProfile = persistentUserProfile.get();

        log.debug("Found user profile: {}", userProfile);
        return userMapper.userProfileToUserDTO(userProfile);
    }

    @Transactional(readOnly = true)
    @Override
    public UserInfoDTO getUserInfoById(final UUID userId) {
        final var persistentUserInfo = usersServiceInfoRepository.findById(userId);
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with id " + userId + " not found");
        }

        final var userInfo = persistentUserInfo.get();

        log.debug("Found user jwt info: {}", userInfo);
        return userMapper.userServiceInfoToUserInfoDTO(userInfo);
    }

    @Transactional
    @Override
    public void deleteUserById(final UUID userId) {
        final var persistentUserInfo = usersServiceInfoRepository.findById(userId);
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with id " + userId + " not found");
        }

        final var userInfo = persistentUserInfo.get();
        userInfo.setDeleted(true);
        userInfo.getUserProfile().setDeleted(true);

        usersServiceInfoRepository.saveAndFlush(userInfo);
    }

    @Transactional
    @Override
    public void changePassword(final ChangePasswordRequestDTO changePasswordRequest) {
        final var persistentUserInfo = usersServiceInfoRepository.findById(changePasswordRequest.getUserId());
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with id " + changePasswordRequest.getUserId() + " not found");
        }

        final var userInfo = persistentUserInfo.get();

        if (!passwordConverter.getPasswordEncoder().matches(changePasswordRequest.getOldPassword(), userInfo.getPassword())) {
            log.trace("password from db {}, provided old password {}", userInfo.getPassword(), passwordConverter.convertToDatabaseColumn(changePasswordRequest.getOldPassword()));
            throw new IllegalArgumentException("Old password is incorrect");
        }

        userInfo.setPassword(changePasswordRequest.getNewPassword());
        log.trace("Changed password for user {}", userInfo.getLogin());

        usersServiceInfoRepository.saveAndFlush(userInfo);
    }

    @Transactional
    @Override
    public void verifyEmail(final String verificationToken) {
        final var persistentUserInfo = usersServiceInfoRepository.findByEmailVerificationToken(verificationToken);
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with verification token " + verificationToken + " not found");
        }

        final var userInfo = persistentUserInfo.get();

        var newRole = roleRepository.findOneByRole(RoleEnum.USER)
                .orElseThrow(() -> new IllegalStateException("Role " + RoleEnum.USER + " not found"));
        userInfo.setRole(newRole);
        log.trace("Set new role {} to user {}", newRole, userInfo.getLogin());

        userInfo.setEmailVerificationToken(null);
        log.trace("Set email verification token to null for user {}", userInfo.getLogin());

        usersServiceInfoRepository.saveAndFlush(userInfo);
    }

    /**
     * Checks if login is available
     *
     * @param login login to check
     * @return true if login is available, false otherwise
     */
    @Transactional(readOnly = true)
    @Override
    public boolean checkLoginAvailability(final String login) {
        return usersServiceInfoRepository.findByLogin(login).isEmpty();
    }

    @Transactional
    @Override
    public UserInfoDTO saveResetPasswordToken(String token, int expirationTimeHours, String login) {
        final var persistentUserInfo = usersServiceInfoRepository.findByLogin(login);
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with login " + login + " not found");
        }

        final var userInfo = persistentUserInfo.get();

        userInfo.setPasswordResetToken(token);
        log.trace("Set password reset token {} for user {}", token, userInfo.getLogin());

        userInfo.setPasswordResetTokenExpiredAt(Timestamp.valueOf(LocalDateTime.now().plusHours(expirationTimeHours)));
        log.trace("Set password reset token expired at {} for user {}", userInfo.getPasswordResetTokenExpiredAt(), userInfo.getLogin());

        final var updatedUser = usersServiceInfoRepository.saveAndFlush(userInfo);
        return userMapper.userServiceInfoToUserInfoDTO(updatedUser);
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequestDTO resetPasswordRequest) {
        final var persistentUserInfo = usersServiceInfoRepository.findByPasswordResetToken(resetPasswordRequest.getResetPasswordToken());
        if (persistentUserInfo.isEmpty()) {
            throw new UserNotFountException("User with reset password token " + resetPasswordRequest.getResetPasswordToken() + " not found");
        }

        final var userInfo = persistentUserInfo.get();
        // TODO check convert between LocalDateTime and Timestamp
        if (userInfo.getPasswordResetTokenExpiredAt().toLocalDateTime().isBefore(LocalDateTime.now())) {
            log.trace("Reset password token expired for user {}", userInfo.getLogin());
            throw new IllegalArgumentException("Reset password token expired");
        }

        userInfo.setPassword(resetPasswordRequest.getNewPassword());
        log.trace("Changed password for user {}", userInfo.getLogin());
        userInfo.setPasswordResetToken(null);
        log.trace("Set password reset token to null for user {}", userInfo.getLogin());

        usersServiceInfoRepository.saveAndFlush(userInfo);
    }

    @Transactional
    @Override
    public void saveVerificationToken(UUID userId, String token) {
        usersServiceInfoRepository.findById(userId)
                .ifPresentOrElse(userInfo -> {
                    userInfo.setEmailVerificationToken(token);
                    usersServiceInfoRepository.saveAndFlush(userInfo);
                }, () -> {
                    throw new UserNotFountException("User with id " + userId + " not found");
                });
    }

}
