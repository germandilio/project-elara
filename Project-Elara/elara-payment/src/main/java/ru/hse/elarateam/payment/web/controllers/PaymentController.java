package ru.hse.elarateam.payment.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.payment.dto.info.PaymentDetailsInfoDTO;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

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
    @ResponseStatus(code = HttpStatus.OK, reason = "Payment was registered.")
    public void registerPayment(@RequestHeader("Authorization") String token,
                                @RequestBody PaymentDetailsInfoDTO paymentDetailsInfoDTO) {
    }
}
