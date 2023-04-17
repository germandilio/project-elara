package ru.hse.elarateam.adminconsole.auth;


import ru.hse.elarateam.adminconsole.auth.dto.UserServiceInfoDTO;

public interface AuthenticationManager {
    /**
     * Authenticates user by token.
     *
     * @param jwtToken token to authenticate
     * @return user info
     * @throws IllegalArgumentException if token is invalid
     * @throws IllegalStateException    if cannot connect to login service
     */
    UserServiceInfoDTO authenticate(String jwtToken);
}
