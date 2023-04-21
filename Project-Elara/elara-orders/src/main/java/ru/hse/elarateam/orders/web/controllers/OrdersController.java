package ru.hse.elarateam.orders.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;
import ru.hse.elarateam.orders.model.status.OrderStatus;
import ru.hse.elarateam.orders.web.services.OrdersService;
import ru.hse.elarateam.orders.web.services.auth.AuthenticationManager;
import ru.hse.elarateam.orders.web.services.auth.dto.RoleEnum;
import ru.hse.elarateam.orders.web.services.jwt.ServiceTokenUtilsImpl;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    // todo schedule deletion of orders with status < PAID every 15 minutes
    private final OrdersService ordersService;
    private final AuthenticationManager authenticationManager;

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
    @GetMapping("/user/{userId}")
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
     * For users.
     *
     * @param token       user JWT token.
     * @param askingAdmin true if admin role is required.
     * @return true if user is not authenticated.
     */
    private boolean notAuthenticated(String token, boolean askingAdmin) {
        if (token == null || !token.startsWith("Bearer ")) {
            log.info("Token is not valid (precheck): " + token);
            return true;
        }
        try {
            var userServiceInfo = authenticationManager.authenticate(token.substring(7));
            log.info("User found: " + userServiceInfo.getLogin() + " role: " + userServiceInfo.getRoleName());
            if(askingAdmin){
                log.info("cheking admin");
            return !Objects.equals(userServiceInfo.getRoleName(), RoleEnum.ADMIN);
            }
            return false;
        } catch (IllegalArgumentException e) {
            log.info("Token is not valid: " + token);
            return true;
        }
    }

}
