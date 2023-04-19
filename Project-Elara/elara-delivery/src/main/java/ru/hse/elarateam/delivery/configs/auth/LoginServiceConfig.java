package ru.hse.elarateam.delivery.configs.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for calling login jwt.
 * Provides {@link AuthTokenInterceptor} bean to add auth header with JWT token to requests.
 */
@Configuration
public class LoginServiceConfig {
    @Value("${elara.login-service.token}")
    private String loginServiceToken;

    @Bean
    public AuthTokenInterceptor loginAuthTokenInterceptor() {
        return new AuthTokenInterceptor(loginServiceToken);
    }
}
