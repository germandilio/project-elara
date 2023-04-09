package ru.hse.elarateam.users.web.services.tokens;

import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

public interface ServiceTokenUtils {

    String generateToken();

    boolean validateToken(String token, EmailServiceInfo emailServiceInfo);
}
