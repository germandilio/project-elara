package ru.hse.elarateam.orders.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.orders.auth.AuthenticationManager;
import ru.hse.elarateam.orders.auth.dto.RoleEnum;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.info.ShipmentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;
import ru.hse.elarateam.orders.model.status.OrderStatus;
import ru.hse.elarateam.orders.services.OrdersService;
import ru.hse.elarateam.orders.services.jwt.ServiceTokenUtilsImpl;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;
    private final AuthenticationManager authenticationManager;
    private final ServiceTokenUtilsImpl serviceTokenUtils;

    /**
     * Place order by orderRequestDTO.
     * Allocated requested products, creates order and returns orderResponseDTO.
     *
     * @param token           JWT token.
     * @param orderRequestDTO order request info.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products allocated.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestHeader("Authorization") String token,
                                                       @RequestBody OrderRequestDTO orderRequestDTO) {
        if (notAuthenticated(token, false)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.placeOrder(orderRequestDTO));
    }

    /**
     * SERVICE ENDPOINT.
     * Delivery jwt target endpoint.
     *
     * @param serviceToken           jwt authorization.
     * @param orderId                order id.
     * @param shipmentDetailsInfoDTO shipment details - must be saved in orders db beforehand.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery details changed.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/delivery")
    public ResponseEntity<OrderResponseDTO> changeShipmentDetails(@RequestHeader("Authorization") String serviceToken,
                                                                  @RequestParam("orderId") UUID orderId,
                                                                  @RequestBody ShipmentDetailsInfoDTO shipmentDetailsInfoDTO) {
        if (serviceTokenInvalid(serviceToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.changeShipmentDetails(shipmentDetailsInfoDTO, orderId));
    }

    /**
     * SERVICE ENDPOINT.
     * Payment jwt target endpoint.
     *
     * @param serviceToken          jwt authorization.
     * @param orderId               order id.
     * @param paymentDetailsInfoDTO payment details - must be saved in orders db beforehand.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment details changed.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/payment")
    public ResponseEntity<OrderResponseDTO> changePaymentDetails(@RequestHeader("Authorization") String serviceToken,
                                                                 @RequestParam("orderId") UUID orderId,
                                                                 @RequestBody PaymentDetailsInfoDTO paymentDetailsInfoDTO) {
        if (serviceTokenInvalid(serviceToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.changePaymentDetails(paymentDetailsInfoDTO, orderId));
    }

    /**
     * Get order by id.
     *
     * @param token   JWT token.
     * @param orderId order id.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@RequestHeader("Authorization") String token,
                                                         @PathVariable("orderId") UUID orderId) {
        if (notAuthenticated(token, false)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.getOrderById(orderId));
    }


    /**
     * Get orders by user id.
     *
     * @param token    JWT token.
     * @param userId   user id.
     * @param pageable automatically parses page parameters.
     * @return page of orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> getOrdersByUserId(@RequestHeader("Authorization") String token,
                                                                    @PathVariable("userId") UUID userId,
                                                                    @ParameterObject Pageable pageable) {
        if (notAuthenticated(token, true)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.getOrdersByUserId(userId, pageable));
    }

    /**
     * Get all orders.
     *
     * @param token    JWT token.
     * @param pageable automatically parses page parameters.
     * @return page of orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @GetMapping("/")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(@RequestHeader("Authorization") String token,
                                                               @ParameterObject Pageable pageable) {
        if (notAuthenticated(token, true)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ordersService.getAllOrders(pageable));
    }

    /**
     * Change order status.
     *
     * @param token   JWT token.
     * @param orderId order id.
     * @param status  new status.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status changed.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @PutMapping("/change-status")
    public ResponseEntity<OrderResponseDTO> changeOrderStatusAdmin(@RequestHeader("Authorization") String token,
                                                                   @RequestParam("orderId") UUID orderId,
                                                                   @RequestParam("status") String status) {
        if (notAuthenticated(token, true)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        var statusEnum = OrderStatus.valueOf(status);
        return ResponseEntity.ok(ordersService.changeOrderStatus(orderId, statusEnum));
    }


    /**
     * SERVICE ENDPOINT.
     *
     * @param serviceToken JWT token.
     * @param orderId      order id.
     * @param status       new status.
     * @return orderResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status changed.",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Order not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @PutMapping("/service/change-status")
    public ResponseEntity<OrderResponseDTO> changeOrderStatusSystem(@RequestHeader("Authorization") String serviceToken,
                                                                    @RequestParam("orderId") UUID orderId,
                                                                    @RequestParam("status") String status) {
        if (serviceTokenInvalid(serviceToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var statusEnum = OrderStatus.valueOf(status);
        return ResponseEntity.ok(ordersService.changeOrderStatus(orderId, statusEnum));
    }

    /**
     * For users.
     *
     * @param token       user JWT token.
     * @param askingAdmin true if admin role is required.
     * @return true if user is not authenticated.
     */
    private boolean notAuthenticated(String token, boolean askingAdmin) {
        if (token == null || !token.startsWith("Bearer ")) {
            return true;
        }
        try {
            var userServiceInfo = authenticationManager.authenticate(token.substring(7));
            return userServiceInfo.getRoleName() != RoleEnum.ADMIN || !askingAdmin;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    /**
     * For services.
     *
     * @param serviceToken service JWT token.
     * @return true if token is valid.
     */
    private boolean serviceTokenInvalid(String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer ")) {
            return true;
        }
        return !serviceTokenUtils.validateToken(serviceToken.substring(7));
    }

}
