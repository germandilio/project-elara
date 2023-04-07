package ru.hse.elarateam.email.web.services.email;

import com.postmarkapp.postmark.client.ApiClient;
import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.email.dto.OrderCheckoutDTO;
import ru.hse.elarateam.email.dto.UserDTO;
import ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout.OrderCheckoutInitializer;
import ru.hse.elarateam.email.web.services.email.messagebuilders.resetpassword.ResetPasswordInitializer;
import ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail.VerifyEmailInitializer;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter(onMethod_ = @Autowired)
public class EmailServiceImpl implements EmailService {
    private final ApiClient emailApiClient;

    private OrderCheckoutInitializer orderCheckoutInitializer;

    private ResetPasswordInitializer resetPasswordInitializer;

    private VerifyEmailInitializer verifyEmailInitializer;


    @Override
    public void sendResetPasswordEmail(UserDTO user, String token) throws PostmarkException, IOException {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        var emailBuilder = resetPasswordInitializer.init(user.getEmail(), user.getFirstName(), token);
        log.trace("Email builder: {}", emailBuilder);
        final var email = emailBuilder.build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }

    @Override
    public void sendConfirmationRegistrationEmail(UserDTO user, String token) throws PostmarkException, IOException {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        var emailBulder = verifyEmailInitializer.init(user.getEmail(), user.getFirstName(), token);
        log.trace("Email builder: {}", emailBulder);
        final var email = emailBulder.build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }

    @Override
    public void sendOrderCheckoutEmail(UserDTO user, OrderCheckoutDTO orderCheckoutDTO) throws PostmarkException, IOException {
        if (orderCheckoutDTO == null) {
            throw new IllegalArgumentException("OrderCheckoutDTO cannot be null");
        }

        var emailBuilder = orderCheckoutInitializer.init(user.getEmail(), user.getFirstName(), orderCheckoutDTO);
        log.trace("Email builder: {}", emailBuilder);
        final var email = emailBuilder.build();

        var response = emailApiClient.deliverMessageWithTemplate(email);
        log.debug("Email sent, response: {}", response);
    }
}
