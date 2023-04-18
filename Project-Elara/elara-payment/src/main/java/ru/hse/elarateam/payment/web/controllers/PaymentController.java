package ru.hse.elarateam.payment.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.payment.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.payment.web.services.PaymentService;
import ru.hse.elarateam.payment.web.services.auth.AuthenticationManagerImpl;

@Slf4j
@RestController
@RequestMapping("/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final AuthenticationManagerImpl authenticationManager;

    /**
     * Register payment.
     *
     * @param token                 JWT token.
     * @param paymentDetailsInfoDTO payment details.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment was registered."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping("/")
    public ResponseEntity<?> registerPayment(@RequestHeader("Authorization") String token,
                                             @RequestBody PaymentDetailsInfoDTO paymentDetailsInfoDTO) {
        //todo maybe send email
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        paymentService.registerPayment(paymentDetailsInfoDTO);
        return ResponseEntity.ok("Payment registered.");
    }

    private boolean notAuthenticated(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            log.info("Token is not valid (precheck): " + token);
            return true;
        }
        try {
            var userServiceInfo = authenticationManager.authenticate(token.substring(7));
            log.info("User found: " + userServiceInfo.getLogin() + " role: " + userServiceInfo.getRoleName());
            return false;
        } catch (IllegalArgumentException e) {
            log.info("Token is not valid: " + token);
            return true;
        }
    }
}
