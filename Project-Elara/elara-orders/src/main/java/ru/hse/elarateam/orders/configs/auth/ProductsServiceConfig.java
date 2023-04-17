package ru.hse.elarateam.orders.configs.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for calling products jwt.
 * Provides {@link AuthTokenInterceptor} bean to add auth header with JWT token to requests.
 */
@Configuration
public class ProductsServiceConfig {
    @Value("${elara.products-service.token}")
    private String productsServiceToken;

    @Bean
    public AuthTokenInterceptor authTokenInterceptor() {
        return new AuthTokenInterceptor(productsServiceToken);
    }
}
