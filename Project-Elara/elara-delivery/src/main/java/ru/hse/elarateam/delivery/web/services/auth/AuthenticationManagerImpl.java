package ru.hse.elarateam.delivery.web.services.auth;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.delivery.web.services.auth.dto.UserServiceInfoDTO;
import ru.hse.elarateam.delivery.web.services.auth.loginservice.LoginServiceFeignClient;

/**
 * Implementation of {@link AuthenticationManager}.
 * Uses {@link LoginServiceFeignClient} to authenticate user.
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
     * @throws IllegalStateException    if cannot connect to login jwt or internal error
     */
    @Override
    public UserServiceInfoDTO authenticate(String jwtToken) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            throw new IllegalArgumentException("Token is empty");
        }
        try {
            return loginServiceFeignClient.getUserInfoByToken(jwtToken);
        } catch (FeignException.Unauthorized ex) {
            // indicates that token is invalid
            throw new IllegalArgumentException(ex);
        } catch (FeignException.BadRequest ex) {
            // internal error
            log.error("Bad request to login jwt ", ex);
            throw new IllegalStateException("Bad request to login jwt", ex);
        } catch (FeignException.InternalServerError ex) {
            // internal fatal error
            log.error("Login jwt is unavailable");
            throw new IllegalStateException("Login jwt is unavailable", ex);
        } catch (Exception ex) {
            log.error("Error while getting user info by token", ex);
            throw new IllegalStateException(ex);
        }
    }
}
