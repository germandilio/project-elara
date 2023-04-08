package ru.hse.elarateam.email.configs.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
