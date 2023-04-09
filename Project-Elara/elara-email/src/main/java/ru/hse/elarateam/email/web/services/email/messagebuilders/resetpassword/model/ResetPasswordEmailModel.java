package ru.hse.elarateam.email.web.services.email.messagebuilders.resetpassword.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ResetPasswordEmailModel {
    private String name;
    private String productName;
    private String actionUrl;
    private String companyName;
    private String companyAddress;
}
