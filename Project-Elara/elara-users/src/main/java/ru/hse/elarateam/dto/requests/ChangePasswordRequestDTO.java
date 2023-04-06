package ru.hse.elarateam.dto.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ChangePasswordRequestDTO {
    private UUID userId;
    private String oldPassword;
    private String newPassword;
}
