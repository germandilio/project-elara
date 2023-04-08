package ru.hse.elarateam.users.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.users.web.services.validation.PasswordConstraint;

import java.util.UUID;

@Data
@Builder
public class ChangePasswordRequestDTO {
    @NotNull
    private UUID userId;

    @NotNull
    private String oldPassword;

    @NotNull
    @PasswordConstraint
    private String newPassword;
}
