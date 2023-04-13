package ru.hse.elarateam.users.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProfileResponse {
    private String token;
    private final String type = "Bearer";

    private UserDTO user;
}
