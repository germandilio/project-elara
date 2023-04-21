package ru.hse.elarateam.orders.configs.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which adds authorization header to request.
 * Default authentication header type is Bearer.
 * <p>
 * For more info about JWT:
 *
 * @see <a href="https://jwt.io/introduction">...</a>
 * <p>
 */
@Slf4j
public class AuthTokenInterceptor implements RequestInterceptor {
    private static final String authType = "Bearer";

    public AuthTokenInterceptor(String loginAuthToken, String productsAuthToken) {
        this.loginAuthToken = loginAuthToken;
        this.productsAuthToken = productsAuthToken;
    }

    private final String loginAuthToken;
    private final String productsAuthToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (requestTemplate.request().url().contains("/api/v1/auth")) {
            requestTemplate.header("Authorization", authType + " " + loginAuthToken);
            log.debug("Added authorization header {} , with token to request: {}", loginAuthToken, requestTemplate);
        } else if (requestTemplate.request().url().contains("/api/v1/products")) {
            requestTemplate.header("Authorization", authType + " " + productsAuthToken);
            log.debug("Added authorization header {} , with token to request: {}", productsAuthToken, requestTemplate);
        } else {
            log.error("Unknown api call");
        }

    }
}
