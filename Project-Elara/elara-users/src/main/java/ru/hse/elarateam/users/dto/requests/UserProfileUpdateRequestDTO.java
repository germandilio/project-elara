package ru.hse.elarateam.users.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class UserProfileUpdateRequestDTO {
    @NotNull
    private UUID userId;

    @NotNull
    @Length(min = 1, max = 255)
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    private String pictureUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
