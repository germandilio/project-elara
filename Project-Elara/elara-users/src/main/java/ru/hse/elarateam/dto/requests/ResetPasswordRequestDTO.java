package ru.hse.elarateam.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ResetPasswordRequestDTO {
    private UUID userId;
    private String oldPassword;
    private String newPassword;
}
