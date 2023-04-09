package ru.hse.elarateam.email.web.controllers;

import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.email.dto.OrderCheckoutDTO;
import ru.hse.elarateam.email.dto.UserDTO;
import ru.hse.elarateam.email.web.services.email.EmailService;
import ru.hse.elarateam.email.web.services.user.UserService;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/email")
public class EmailController {

    private final EmailService emailService;

    private final UserService userService;

    // TODO secure this service: add JWT token or OAuth and not expose this to gateway
    //  rewrite to use async messaging instead of rest api call (time to send email ~ 1-2 sec)

    @PostMapping("/reset-password")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email with password reset link was sent")
    public void sendEmailToResetPassword(@RequestParam("userId") UUID userID, @RequestParam("token") String token) throws IOException, PostmarkException {
        UserDTO user = userService.getUserById(userID);

        emailService.sendResetPasswordEmail(user, token);
    }

    @PostMapping("/confirm-registration")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email with registration confirmation was sent")
    public void sendEmailToConfirmRegistration(@RequestParam("userId") UUID userID, @RequestParam("token") String token) throws PostmarkException, IOException {
        UserDTO user = userService.getUserById(userID);

        emailService.sendConfirmationRegistrationEmail(user, token);
    }

    @PostMapping("/order-checkout")
    @ResponseStatus(code = HttpStatus.OK, reason = "Email with order checkout details was sent")
    public void sendEmailWithOrderCheckoutDetails(@RequestParam("userId") UUID userID, @RequestBody OrderCheckoutDTO orderCheckoutDTO) throws PostmarkException, IOException {
        UserDTO user = userService.getUserById(userID);

        emailService.sendOrderCheckoutEmail(user, orderCheckoutDTO);
    }


}
