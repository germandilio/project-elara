package ru.hse.elarateam.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.dto.UserInfoDTO;
import ru.hse.elarateam.dto.UserProfileDTO;
import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.model.RoleEnum;
import ru.hse.elarateam.model.UserServiceInfo;
import ru.hse.elarateam.web.mappers.UserMapper;
import ru.hse.elarateam.web.repositories.RoleRepository;
import ru.hse.elarateam.web.repositories.UsersProfileRepository;
import ru.hse.elarateam.web.repositories.UsersServiceInfoRepository;
import ru.hse.elarateam.web.services.validation.UserValidator;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UsersService {

    private final UsersServiceInfoRepository usersServiceInfoRepository;

    private final UsersProfileRepository usersProfileRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final UserValidator userValidator;

    @Override
    public void saveUser(UserRegisterRequestDTO userRegisterRequest) {
        // validation will throw exception if something is invalid
        userValidator.validateUserRegisterRequest(userRegisterRequest);

        var initialRole = roleRepository.findOneByRole(RoleEnum.EMAIL_NOT_VERIFIED)
                .orElseThrow(() -> new IllegalStateException("Role " + RoleEnum.EMAIL_NOT_VERIFIED + " not found"));

        var userProfile = userMapper.userRegisterRequestDTOtoUserProfile(userRegisterRequest);
        var savedProfile = usersProfileRepository.saveAndFlush(userProfile);

        var userServiceInfo = UserServiceInfo.builder()
                .login(userRegisterRequest.getLogin())
                .password(userRegisterRequest.getPassword())
                .userProfile(savedProfile)
                .role(initialRole)
                .build();

        usersServiceInfoRepository.saveAndFlush(userServiceInfo);

        // TODO sent email with verification link
    }

    @Override
    public UserProfileDTO updateUserProfile(UserProfileUpdateRequestDTO userProfileUpdateRequest) {
        userValidator.validateUserProfileUpdateRequest(userProfileUpdateRequest);
        return null;
    }

    @Override
    public UserProfileDTO getUserProfileById(UUID userId) {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfoById(UUID userId) {
        return null;
    }

    @Override
    public void deleteUserById(UUID userId) {

    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequest) {
        userValidator.validateChangePasswordRequest(changePasswordRequest);

    }

    @Override
    public UserInfoDTO getUserInfoByResetPasswordToken(String token) {
        return null;
    }

    @Override
    public UserInfoDTO getUserInfoById() {
        return null;
    }

    @Override
    public void verifyEmail(String verificationToken) {

    }

    /**
     * Checks if login is available
     * @param login login to check
     * @return true if login is available, false otherwise
     */
    @Override
    public boolean checkLoginAvailability(String login) {
        return usersServiceInfoRepository.findByLogin(login).isEmpty();
    }
}
