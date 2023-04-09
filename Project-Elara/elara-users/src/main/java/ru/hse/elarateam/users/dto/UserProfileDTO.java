package ru.hse.elarateam.users.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class UserProfileDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
