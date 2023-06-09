package ru.hse.elarateam.users.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.hse.elarateam.users.web.services.validation.PasswordConstraint;

import java.sql.Date;

@Data
@Builder
public class UserRegisterRequestDTO {
    @NotNull
    @Length(min = 1, max = 255)
    @Email
    private String login;

    @NotNull
    @PasswordConstraint
    private String password;

    @NotNull
    @Length(min = 1, max = 255)
    private String firstName;

    @NotNull
    @Length(min = 1, max = 255)
    private String lastName;

    private String pictureUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
