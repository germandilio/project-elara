package ru.hse.elarateam.users.web.services.tokens;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


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
        final String subject = getClaimFromToken(token, Claims::getSubject);
        final String issuer = getClaimFromToken(token, Claims::getIssuer);
        final String audience = getClaimFromToken(token, Claims::getAudience);

        return (subject.equals(emailServiceInfo.getSub()) &&
                issuer.equals(emailServiceInfo.getIss()) &&
                audience.equals(emailServiceInfo.getAud()));

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
