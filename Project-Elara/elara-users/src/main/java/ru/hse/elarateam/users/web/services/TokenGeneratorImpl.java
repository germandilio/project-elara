package ru.hse.elarateam.users.web.services;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Setter
@RequiredArgsConstructor
@Component
public class TokenGeneratorImpl implements TokenGenerator {

    @Value("${token-factory.token.length}")
    private int tokenLength = 64;

    @Override
    public String generate() {
        return generateSimple();
    }

    private String generateSecure() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[tokenLength];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Generates a random string of the specified length.
     * Does not contain any special characters.
     *
     * @return a random string of the specified length
     */
    private String generateSimple() {
        return RandomStringUtils.random(tokenLength, true, true);
    }
}
