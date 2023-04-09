package ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VerifyEmailModel {
    private String name;
    private String productName;
    private String login;
    private String actionUrl;
    private String companyName;
    private String companyAddress;
}
