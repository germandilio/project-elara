package ru.hse.elarateam.users.web.services.tokens.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * Helper class for generating and validating JWT tokens.
 */
@Slf4j
@Setter
@Component
public class JWTUtilsImpl implements JWTUtils {
    @Value("${token-factory.token.secret}")
    private String secret;

    @Value("${elara.login.jwt.issuer}")
    private String issuer;

    @Value("${elara.login.jwt.audience}")
    private String audience;

    /**
     * Generates JWT token for given login.
     *
     * @param login login to generate token for
     * @return generated token
     */
    @Override
    public String generateToken(String login) {
        return Jwts.builder()
                .setIssuer(issuer)
                .setAudience(audience)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(login)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validates JWT token and returns login from it.
     *
     * @param token token to validate
     * @return login from token or null if token is invalid
     */
    @Override
    public String getUserLoginFromToken(String token) {
        if (token == null) {
            log.trace("Token is null");
            return null;
        }

        try {
            final var body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            if (!Objects.equals(body.getIssuer(), issuer) || !Objects.equals(body.getAudience(), audience)) {
                log.trace("Token is invalid");
                return null;
            }
            return body.getSubject();

        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return null;
        }
    }
}
