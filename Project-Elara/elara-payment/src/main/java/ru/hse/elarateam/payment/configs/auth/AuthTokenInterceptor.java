package ru.hse.elarateam.payment.configs.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Class which adds authorization header to request.
 * Default authentication header type is Bearer.
 * <p>
 * For more info about JWT:
 *
 * @see <a href="https://jwt.io/introduction">...</a>
 * <p>
 * Example of usage:
 * {@link LoginServiceConfig}
 */
@Slf4j
@RequiredArgsConstructor
public class AuthTokenInterceptor implements RequestInterceptor {
    private static final String authType = "Bearer";

    private final String authToken;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", authType + " " + authToken);
        log.debug("Added authorization header {} , with token to request: {}", authToken, requestTemplate);
    }
}
