package ru.hse.elarateam.login.web.services.users;

import ru.hse.elarateam.login.dto.UserServiceInfoDTO;

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
     * Returns user info by user login
     *
     * @param login user login
     * @return user info
     */
    UserServiceInfoDTO getUserInfoByLogin(String login);
}
