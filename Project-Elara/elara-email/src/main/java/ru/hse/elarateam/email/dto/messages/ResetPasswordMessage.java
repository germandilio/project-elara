package ru.hse.elarateam.email.dto.messages;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ResetPasswordMessage {
    private UUID userId;
    private String resetPasswordToken;
}
