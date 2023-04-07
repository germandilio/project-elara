package ru.hse.elarateam.web.services.email;

import java.util.UUID;

public interface EmailService {
    void sendEmailVerification(UUID userId, String verificationToken);

    void sendResetPasswordEmail(UUID userId, String resetPasswordToken);
}
