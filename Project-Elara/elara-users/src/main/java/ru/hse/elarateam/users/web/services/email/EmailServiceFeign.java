package ru.hse.elarateam.users.web.services.email;

import feign.FeignException;
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
        try {
            emailServiceFeignClient.sendConfirmRegistrationEmail(userId, verificationToken);
        } catch (FeignException ex) {
            if (ex.status() == 400) {
                log.error("Error while sending email verification", ex);
                throw new IllegalStateException("Error while sending email verification", ex);
            }
            if (ex.status() == 500) {
                log.error("Mailing service unavailable", ex);
                throw new IllegalStateException("Mailing service unavailable", ex);
            }
        }
    }

    @Override
    public void sendResetPasswordEmail(UUID userIdl, String resetPasswordToken) {
        log.debug("Sending reset password email to user with id {} and token {}", userIdl, resetPasswordToken);
        try {
            emailServiceFeignClient.sendResetPasswordEmail(userIdl, resetPasswordToken);
        } catch (FeignException ex) {
            if (ex.status() == 400) {
                log.error("Error while sending email verification", ex);
                throw new IllegalStateException("Error while sending email verification", ex);
            }
            if (ex.status() == 500) {
                log.error("Mailing service unavailable", ex);
                throw new IllegalStateException("Mailing service unavailable", ex);
            }
        }
    }
}
