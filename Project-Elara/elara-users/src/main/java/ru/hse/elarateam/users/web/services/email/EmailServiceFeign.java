package ru.hse.elarateam.users.web.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailServiceFeign implements EmailService {

    private final EmailServiceFeignClient emailServiceFeignClient;

    @Override
    public void sendEmailVerification(UUID userId, String verificationToken) {
        log.debug("Sending email verification to user with id {} and token {}", userId, verificationToken);
        emailServiceFeignClient.sendConfirmRegistrationEmail(userId, verificationToken);
    }

    @Override
    public void sendResetPasswordEmail(UUID userIdl, String resetPasswordToken) {
        log.debug("Sending reset password email to user with id {} and token {}", userIdl, resetPasswordToken);
        emailServiceFeignClient.sendResetPasswordEmail(userIdl, resetPasswordToken);
    }
}
