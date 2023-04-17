package ru.hse.elarateam.users.dto.messages;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmailVerificationMessage {
    private UUID userId;
    private String verificationToken;
}
