package ru.hse.elarateam.users.web.services.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.users.configs.JMSConfig;
import ru.hse.elarateam.messages.EmailVerificationMessage;
import ru.hse.elarateam.messages.ResetPasswordMessage;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailServiceJMS implements EmailService {

    private final JmsTemplate jmsTemplate;

    @Override
    public void sendEmailVerification(UUID userId, String verificationToken) {
        final var message = EmailVerificationMessage.builder()
                .userId(userId)
                .verificationToken(verificationToken)
                .build();

        log.debug("Sending email verification to user with id {}: message={}", userId, message);

        jmsTemplate.convertAndSend(JMSConfig.EMAIL_VERIFICATION_QUEUE, message);
    }

    @Override
    public void sendResetPasswordEmail(UUID userId, String resetPasswordToken) {
        final var message = ResetPasswordMessage.builder()
                .userId(userId)
                .resetPasswordToken(resetPasswordToken)
                .build();

        log.debug("Sending reset password email to user with id {}: message={}", userId, message);

        jmsTemplate.convertAndSend(JMSConfig.RESET_PASSWORD_QUEUE, message);
    }
}
