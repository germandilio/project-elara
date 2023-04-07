package ru.hse.elarateam.users.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;
import ru.hse.elarateam.users.web.services.validation.PasswordConstraint;

@Data
@Builder
public class ResetPasswordRequestDTO {
    @NonNull
    @Length(min = 1, max = 128)
    private String resetPasswordToken;

    @NotNull
    @PasswordConstraint
    private String newPassword;
}
