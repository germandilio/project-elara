package ru.hse.elarateam.users.web.services.email;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "elara-email", url = "http://localhost:8081")
public interface EmailServiceFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/email/reset-password", produces = "application/json")
    void sendResetPasswordEmail(@RequestParam("userId") UUID userId, @RequestParam String token);

@RequestMapping(method = RequestMethod.POST, value = "/api/v1/email/confirm-registration", produces = "application/json")
    void sendConfirmRegistrationEmail(@RequestParam("userId") UUID userId, @RequestParam String token);
}
