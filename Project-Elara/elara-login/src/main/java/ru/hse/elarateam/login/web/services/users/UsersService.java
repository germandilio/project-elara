package ru.hse.elarateam.login.web.services.users;

import ru.hse.elarateam.login.dto.UserServiceInfoDTO;
import ru.hse.elarateam.login.model.UserServiceInfo;

import java.util.UUID;

public interface UsersService {
    /**
     * Returns user info by user id
     *
     * @param userId user id
     * @return user info
     */
    UserServiceInfoDTO getUserInfoById(UUID userId);

    /**
     * Perform login operation.
     * If login and password are correct, returns user info.
     *
     * @param login user login
     * @return user info
     */
    UserServiceInfo login(String login, String password);

    /**
     * Returns user info by user login
     *
     * @param login user login
     * @return user info
     */
    UserServiceInfoDTO findUserByLogin(String login);
}
