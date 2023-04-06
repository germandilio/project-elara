package ru.hse.elarateam.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class UserRegisterRequestDTO {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
