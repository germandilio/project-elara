package ru.hse.elarateam.web.services.email;

import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.hse.elarateam.dto.OrderCheckoutDTO;
import ru.hse.elarateam.dto.UserDTO;
import ru.hse.elarateam.web.services.email.messagebuilders.ordercheckout.OrderCheckoutEmailBuilder;
import ru.hse.elarateam.web.services.email.messagebuilders.resetpassword.ResetPasswordEmailBuilder;
import ru.hse.elarateam.web.services.email.messagebuilders.verifyemail.VerifyEmailBuilder;

import java.io.IOException;

public interface EmailService {
    /**
     * @param user  UserDTO object containing user data
     * @param token token from user database
     * @throws PostmarkException if there is an error while building the message (@see <a href="https://postmarkapp.com/developer/api/overview#error-codes">API Error codes</a>)
     * @throws IOException       if it is internal error while sending message
     */
    void sendResetPasswordEmail(UserDTO user, String token) throws PostmarkException, IOException;


    /**
     * @param user    UserDTO object containing user data
     * @param token   token from user database
     * @throws PostmarkException if there is an error while building the message (@see <a href="https://postmarkapp.com/developer/api/overview#error-codes">API Error codes</a>)
     * @throws IOException       if it is internal error while sending message
     */
    void sendConfirmationRegistrationEmail(UserDTO user, String token) throws PostmarkException, IOException;

    /**
     * @param user             UserDTO object containing user data
     * @param orderCheckoutDTO OrderCheckoutDTO object containing order data
     * @throws PostmarkException if there is an error while building the message (@see <a href="https://postmarkapp.com/developer/api/overview#error-codes">API Error codes</a>)
     * @throws IOException       if it is internal error while sending message
     */
    void sendOrderCheckoutEmail(UserDTO user, OrderCheckoutDTO orderCheckoutDTO) throws PostmarkException, IOException;
}