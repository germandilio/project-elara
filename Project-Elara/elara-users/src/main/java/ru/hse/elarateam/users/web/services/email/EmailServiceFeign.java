package ru.hse.elarateam.users.web.services.email;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Implementation of {@link EmailService}.
 * Uses {@link EmailServiceFeignClient} to send emails.
 *
 * @see EmailService
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class EmailServiceFeign implements EmailService {

    private final EmailServiceFeignClient emailServiceFeignClient;

    @Override
    public void sendEmailVerification(UUID userId, String verificationToken) {
        log.debug("Sending email verification to user with id {} and token {}", userId, verificationToken);
        sendEmail(emailServiceFeignClient::sendConfirmRegistrationEmail, userId, verificationToken);
    }

    @Override
    public void sendResetPasswordEmail(UUID userIdl, String resetPasswordToken) {
        log.debug("Sending reset password email to user with id {} and token {}", userIdl, resetPasswordToken);
        sendEmail(emailServiceFeignClient::sendResetPasswordEmail, userIdl, resetPasswordToken);
    }

    /**
     * Sends email using provided consumer.
     *
     * @param consumer consumer that accepts two parameters
     * @param t        first parameter
     * @param u        second parameter
     * @param <T>      type of first parameter
     * @param <U>      type of second parameter
     * @throws IllegalStateException if mailing service is unavailable or error while sending email verification
     */
    private <T, U> void sendEmail(BiConsumer<T, U> consumer, T t, U u) {
        try {
            consumer.accept(t, u);
        } catch (FeignException.BadRequest ex) {
            log.error("Error while sending email verification", ex);
            throw new IllegalStateException("Error while sending email verification", ex);
        } catch (FeignException.InternalServerError ex) {
            log.error("Mailing service unavailable", ex);
            throw new IllegalStateException("Mailing service unavailable", ex);
        } catch (Exception ex) {
            log.error("Error while sending email verification", ex);
            throw new IllegalStateException(ex);
        }
    }
}
