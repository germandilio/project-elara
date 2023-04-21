package ru.hse.elarateam.login.web.services.jwt.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Helper class for generating and validating jwt tokens.
 */
@Slf4j
@Component
public class ServiceTokenUtilImpl implements ServiceTokenUtils {

    @Value("${token-factory.token.secret}")
    private String secret;

    @Value("${login-service.token.sub}")
    private String sub;

    @Value("${login-service.token.iss}")
    private String iss;

    @Value("${login-service.token.aud}")
    private String aud;

    @Override
    public String generateToken() {

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(sub)
                .setIssuer(iss)
                .setAudience(aud)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            final var body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

            return (Objects.equals(body.getSubject(), sub) &&
                    Objects.equals(body.getIssuer(), iss) &&
                    Objects.equals(body.getAudience(), aud));
        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}
