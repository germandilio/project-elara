package ru.hse.elarateam.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class TokenGeneratorImpl implements TokenGenerator {

    @Value("${token-factory.token.length}")
    private int tokenLength = 64;

    @Override
    public String generate() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[tokenLength];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
