package ru.hse.elarateam.web.services.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.web.repositories.UsersServiceInfoRepository;

import java.sql.Date;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserValidatorImpl implements UserValidator {

    private final UsersServiceInfoRepository usersServiceInfoRepository;

    private final PasswordValidator passwordValidator;

    private final EmailValidator emailValidator = EmailValidator.getInstance(true, true);

    @Transactional(readOnly = true)
    @Override
    public void validateUserRegisterRequest(UserRegisterRequestDTO userRegisterRequest) {
        if (userRegisterRequest == null) {
            log.debug("UserRegisterRequestDTO is null");
            throw new IllegalArgumentException("UserRegisterRequestDTO is null");
        }

        // login should not be already assigned
        usersServiceInfoRepository.findByLogin(userRegisterRequest.getLogin())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User with login " + userRegisterRequest.getLogin() + " already exists");
                });

        // password is obligatory and valid
        if (!passwordValidator.isValid(userRegisterRequest.getPassword())) {
            log.debug("New password is invalid");
            throw new IllegalArgumentException("New password is invalid");
        }

        // first name is obligatory and not blank
        fioNotBlank(userRegisterRequest.getFirstName(), userRegisterRequest.getLastName());

        if (userRegisterRequest.getBirthDate() != null && isAfterNow(userRegisterRequest.getBirthDate())) {
            log.debug("Birth date is in future");
            throw new IllegalArgumentException("Birth date is in future");
        }
    }

    @Override
    public void validateUserProfileUpdateRequest(UserProfileUpdateRequestDTO userProfileUpdateRequest) {
        if (userProfileUpdateRequest == null) {
            log.debug("ChangePasswordRequestDTO is null");
            throw new IllegalArgumentException("ChangePasswordRequestDTO is null");
        }

        // userId is obligatory
        if (userProfileUpdateRequest.getUserId() == null) {
            log.debug("User id is null");
            throw new IllegalArgumentException("User id is null");
        }

        // email is obligatory and valid (allowed local and TLDs)
        if (!emailValidator.isValid(userProfileUpdateRequest.getEmail())) {
            log.debug("Email is invalid");
            throw new IllegalArgumentException("Email is invalid");
        }

        // firstName is obligatory and not blank
        fioNotBlank(userProfileUpdateRequest.getFirstName(), userProfileUpdateRequest.getLastName());

        if (userProfileUpdateRequest.getBirthDate() != null && isAfterNow(userProfileUpdateRequest.getBirthDate())) {
            log.debug("Birth date is in future");
            throw new IllegalArgumentException("Birth date is in future");
        }
    }

    @Override
    public void validateChangePasswordRequest(ChangePasswordRequestDTO changePasswordRequest) {
        if (changePasswordRequest == null) {
            log.debug("ChangePasswordRequestDTO is null");
            throw new IllegalArgumentException("ChangePasswordRequestDTO is null");
        }

        // password is obligatory and valid
        if (!passwordValidator.isValid(changePasswordRequest.getNewPassword())) {
            log.debug("New password is invalid");
            throw new IllegalArgumentException("New password is invalid");
        }
    }

    private boolean isAfterNow(Date date) {
        return date.after(new Date(System.currentTimeMillis()));
    }

    private void fioNotBlank(String firstName, String lastName) {
        if (firstName == null || firstName.isBlank()) {
            log.debug("First name is null or blank");
            throw new IllegalArgumentException("First name is null or blank");
        }

        if (lastName == null || lastName.isBlank()) {
            log.debug("Last name is null or blank");
            throw new IllegalArgumentException("Last name is null or blank");
        }
    }
}
