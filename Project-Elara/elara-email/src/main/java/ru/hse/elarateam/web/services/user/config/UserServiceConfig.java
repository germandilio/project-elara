package ru.hse.elarateam.web.services.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.hse.elarateam.web.services.user.config.auth.AuthTokenInterceptor;

/**
 * Configuration class for calling user service.
 * Provides {@link AuthTokenInterceptor} bean to add auth header with JWT token to requests.
 */
@Configuration
public class UserServiceConfig {

    @Value("${elara.user-service.token}")
    private String userServiceToken;

    @Bean
    public AuthTokenInterceptor authTokenInterceptor() {
        return new AuthTokenInterceptor(userServiceToken);
    }
}
