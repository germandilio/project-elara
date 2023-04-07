package ru.hse.elarateam.email.web.services.email;

import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.email.dto.OrderCheckoutDTO;
import ru.hse.elarateam.email.dto.UserDTO;
import ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout.OrderCheckoutEmailBuilder;
import ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout.OrderCheckoutEmailBuilderImpl;
import ru.hse.elarateam.email.web.services.email.messagebuilders.resetpassword.ResetPasswordEmailBuilder;
import ru.hse.elarateam.email.web.services.email.messagebuilders.resetpassword.ResetPasswordEmailBuilderImpl;
import ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail.VerifyEmailBuilder;
import ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail.VerifyEmailBuilderImpl;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final ApiClient emailApiClient;

    private ResetPasswordEmailBuilder getResetPasswordEmailBuilder() {
        var builder = new ResetPasswordEmailBuilderImpl();
        log.debug("Create ResetPasswordEmailBuilder: {}", builder);
        return builder;
    }

    private VerifyEmailBuilder getVerifyEmailBuilder() {
        var builder = new VerifyEmailBuilderImpl();
        log.debug("Create VerifyEmailBuilderImpl: {}", builder);
        return builder;
    }

    private OrderCheckoutEmailBuilder getOrderCheckoutEmailBuilder() {
        var builder = new OrderCheckoutEmailBuilderImpl();
        log.debug("Create OrderCheckoutEmailBuilderImpl: {}", builder);
        return builder;
    }


    @Override
    public void sendResetPasswordEmail(UserDTO user, String token) throws PostmarkException, IOException {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        var email = getResetPasswordEmailBuilder()
                .setRecipientEmail(user.getEmail())
                .setResetToken(token)
                .setUserName(user.getFirstName())
                .build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }

    @Override
    public void sendConfirmationRegistrationEmail(UserDTO user, String token) throws PostmarkException, IOException {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        var email = getVerifyEmailBuilder()
                .setRecipientEmail(user.getEmail())
                .setVerificationToken(token)
                .setUserName(user.getFirstName())
                .build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }

    @Override
    public void sendOrderCheckoutEmail(UserDTO user, OrderCheckoutDTO orderCheckoutDTO) throws PostmarkException, IOException {
        var email = getOrderCheckoutEmailBuilder()
                .setUserName(user.getFirstName())
                .setRecipientEmail(user.getEmail())
                .setOrderDetails(orderCheckoutDTO)
                .build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }
}
