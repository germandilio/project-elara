package ru.hse.elarateam.login.dto;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.login.model.RoleEnum;

import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private UUID id;

    private UserProfileDTO profile;

    private RoleEnum role;
    private String token;
    private final String type = "Bearer";
}
