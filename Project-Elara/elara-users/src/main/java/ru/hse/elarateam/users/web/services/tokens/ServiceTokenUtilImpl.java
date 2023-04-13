package ru.hse.elarateam.users.web.services.tokens;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

import java.util.Date;
import java.util.Objects;


@Slf4j
@Component
public class ServiceTokenUtilImpl implements ServiceTokenUtils {

    @Value("${token-factory.token.secret}")
    private String secret;

    @Override
    public String generateToken() {
        final var emailServiceInfo = new EmailServiceInfo();

        return Jwts.builder()
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
                    Objects.equals(body.getAudience(), emailServiceInfo.getAud()));
        } catch (Exception e) {
            log.debug("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}
