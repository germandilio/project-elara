package ru.hse.elarateam.orders.services.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;


/**
 * Helper class for generating and validating jwt tokens.
 */
@Slf4j
@Component
public class ServiceTokenUtilsImpl implements ServiceTokenUtils {

    @Value("${token-factory.token.secret}")
    private String secret;

    @Value("${elara.products.jwt.issuer}")
    private String iss;

    @Value("${elara.products.jwt.audience}")
    private String aud;

    @Override
    public String generateToken() {
        return Jwts.builder()
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

            return (Objects.equals(body.getIssuer(), iss) &&
                    Objects.equals(body.getAudience(), aud));
        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}
