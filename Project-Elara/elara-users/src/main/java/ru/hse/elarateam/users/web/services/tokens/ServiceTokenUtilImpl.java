package ru.hse.elarateam.users.web.services.tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


@Slf4j
@Component
public class ServiceTokenUtilImpl implements ServiceTokenUtils {

    @Value("${token-factory.token.secret}")
    private String secret;

    @Override
    public String generateToken() {
        final var emailServiceInfo = new EmailServiceInfo();

        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(emailServiceInfo.getSub())
                .setIssuer(emailServiceInfo.getIss())
                .setAudience(emailServiceInfo.getAud())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public boolean validateToken(String token, EmailServiceInfo emailServiceInfo) {
        try {
            final var body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

            return (Objects.equals(body.getSubject(), emailServiceInfo.getSub()) &&
                    Objects.equals(body.getIssuer(), emailServiceInfo.getIss()) &&
                    Objects.equals(body.getAudience(),emailServiceInfo.getAud()));
        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return false;
        }
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
