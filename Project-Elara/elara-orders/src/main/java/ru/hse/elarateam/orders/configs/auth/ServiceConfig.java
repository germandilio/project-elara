package ru.hse.elarateam.orders.configs.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for calling products jwt.
 * Provides {@link AuthTokenInterceptor} bean to add auth header with JWT token to requests.
 */
@Configuration
public class ServiceConfig {
    @Value("${elara.products-service.token}")
    private String productsServiceToken;

    @Value("${elara.login-service.token}")
    private String loginServiceToken;

    @Bean
    public AuthTokenInterceptor productsAuthTokenInterceptor() {
        return new AuthTokenInterceptor(loginServiceToken, productsServiceToken);
    }
}
