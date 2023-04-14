package ru.hse.elarateam.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.users.model.RoleEnum;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID id;
    private RoleEnum role;
    private String email;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
