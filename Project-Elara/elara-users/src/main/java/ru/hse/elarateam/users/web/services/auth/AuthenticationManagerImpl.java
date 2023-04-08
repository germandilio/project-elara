package ru.hse.elarateam.users.web.services.auth;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.users.web.services.auth.dto.UserServiceInfoDTO;
import ru.hse.elarateam.users.web.services.auth.loginservice.LoginServiceFeignClient;

/**
 * Implementation of {@link AuthenticationManager}.
 * Uses {@link LoginServiceFeignClient} to authenticate user.
 * Handles exceptions from login service.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final LoginServiceFeignClient loginServiceFeignClient;

    /**
     * Authenticates user by token.
     *
     * @param jwtToken token to authenticate
     * @return user info
     * @throws IllegalArgumentException if token is invalid
     * @throws IllegalStateException    if cannot connect to login service
     */
    @Override
    public UserServiceInfoDTO authenticate(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        try {
            return loginServiceFeignClient.getUserInfoByToken(jwtToken);
        } catch (FeignException.Unauthorized ex) {
            throw new IllegalArgumentException(ex);
        } catch (IllegalStateException ex) {
            log.error("Error while getting user info by token", ex);
            throw new IllegalStateException("Cannot connect to login service", ex);
        }
    }
}
