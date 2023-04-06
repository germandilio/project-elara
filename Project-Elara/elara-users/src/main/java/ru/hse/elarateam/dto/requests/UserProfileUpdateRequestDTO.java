package ru.hse.elarateam.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class UserProfileUpdateRequestDTO {
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String pictureUrl;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
}
