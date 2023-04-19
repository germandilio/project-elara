package ru.hse.elarateam.orders.auth;


import ru.hse.elarateam.orders.auth.dto.UserServiceInfoDTO;

public interface AuthenticationManager {
    /**
     * Authenticates user by token.
     *
     * @param jwtToken token to authenticate
     * @return user info
     * @throws IllegalArgumentException if token is invalid
     * @throws IllegalStateException    if cannot connect to login jwt
     */
    UserServiceInfoDTO authenticate(String jwtToken);
}
