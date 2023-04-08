package ru.hse.elarateam.payment.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.payment.dto.info.PaymentDetailsInfoDTO;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.OK, reason = "Payment was registered.")
    public void registerPayment(@RequestBody PaymentDetailsInfoDTO paymentDetailsInfoDTO) {
    }
}
