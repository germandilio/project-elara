package ru.hse.elarateam.email.web.listeners;

import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.email.configs.JMSConfig;
import ru.hse.elarateam.email.dto.messages.EmailVerificationMessage;
import ru.hse.elarateam.email.dto.messages.OrderCheckoutMessage;
import ru.hse.elarateam.email.dto.messages.ResetPasswordMessage;
import ru.hse.elarateam.email.web.services.email.EmailService;
import ru.hse.elarateam.email.web.services.user.UserService;

@Slf4j
@RequiredArgsConstructor
@Component
public class EmailRequestsListener {
    private final EmailService emailService;

    private final UserService userService;

    @JmsListener(destination = JMSConfig.EMAIL_VERIFICATION_QUEUE)
    public void listenForEmailVerificationRequests(EmailVerificationMessage message) {
        if (message.getUserId() == null || message.getVerificationToken() == null) {
            log.error("Received invalid email verification message: {}", message);
            return;
        }

        final var userInfo = userService.getUserById(message.getUserId());
        if (userInfo == null) {
            log.error("Received email verification message for non-existent user: {}", message);
            return;
        }

        if (userInfo.isEmailVerified()) {
            log.error("Received email verification message for already verified user: {}", message);
            return;
        }

        try {
            emailService.sendConfirmationRegistrationEmail(userInfo, message.getVerificationToken());
        } catch (PostmarkException e) {
            if (e.getErrorCode() == 300) {
                log.error("Postmark error: {}", e.getMessage());
            } else {
                log.error("Error while sending email verification message", e);
            }
        } catch (Exception ex) {
            log.error("Error while sending email verification message", ex);
        }
    }

    @JmsListener(destination = JMSConfig.RESET_PASSWORD_QUEUE)
    public void listenForResetPasswordRequests(ResetPasswordMessage message) {
        if (message.getUserId() == null || message.getResetPasswordToken() == null) {
            log.error("Received invalid reset password message: {}", message);
            return;
        }

        final var userInfo = userService.getUserById(message.getUserId());
        if (userInfo == null) {
            log.error("Received reset password message for non-existent user: {}", message);
            return;
        }

        try {
            emailService.sendResetPasswordEmail(userInfo, message.getResetPasswordToken());
        } catch (PostmarkException e) {
            if (e.getErrorCode() == 300) {
                log.error("Postmark error: {}", e.getMessage());
            } else {
                log.error("Error while sending reset password message", e);
            }
        } catch (Exception ex) {
            log.error("Error while sending reset password message", ex);
        }
    }

    @JmsListener(destination = JMSConfig.ORDER_CHECKOUT_QUEUE)
    public void sendOrderCheckoutEmail(OrderCheckoutMessage message) {
        if (message.getUserId() == null || message.getOrderCheckoutDTO() == null) {
            log.error("Received invalid order checkout message: {}", message);
            return;
        }

        final var userInfo = userService.getUserById(message.getUserId());
        if (userInfo == null) {
            log.error("Received order checkout message for non-existent user: {}", message);
            return;
        }

        try {
            emailService.sendOrderCheckoutEmail(userInfo, message.getOrderCheckoutDTO());
        } catch (PostmarkException e) {
            if (e.getErrorCode() == 300) {
                log.error("Postmark error: {}", e.getMessage());
            } else {
                log.error("Error while sending order checkout message", e);
            }
        } catch (Exception ex) {
            log.error("Error while sending order checkout message", ex);
        }
    }
}
