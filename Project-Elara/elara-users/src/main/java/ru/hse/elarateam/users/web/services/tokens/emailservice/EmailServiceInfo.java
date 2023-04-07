package ru.hse.elarateam.users.web.services.tokens.emailservice;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class EmailServiceInfo {
    @Value("${email-service.token.sub}")
    private String sub;

    @Value("${email-service.token.iss}")
    private String iss;

    @Value("${email-service.token.aud}")
    private String aud;
}
