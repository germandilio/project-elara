package ru.hse.elarateam.orders.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.info.ShipmentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrdersController {

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
        return null;
    }

    /**
     * SERVICE ENDPOINT.
     * Delivery service target endpoint.
     *
     * @param serviceToken           service authorization.
     * @param orderId                order id.
     * @param shipmentDetailsInfoDTO shipment details.
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
    public ResponseEntity<OrderResponseDTO> changeDeliveryDetails(@RequestHeader("Authorization") String serviceToken,
                                                                  @RequestParam("orderId") UUID orderId,
                                                                  @RequestBody ShipmentDetailsInfoDTO shipmentDetailsInfoDTO) {
        return null;
    }

    /**
     * SERVICE ENDPOINT.
     * Payment service target endpoint.
     *
     * @param serviceToken          service authorization.
     * @param orderId               order id.
     * @param paymentDetailsInfoDTO payment details.
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
        return null;
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
        return null;
    }


    /**
     * Get orders by user id.
     *
     * @param token      JWT token.
     * @param userId     user id.
     * @param pageNumber page number.
     * @param pageSize   page size.
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
                                                                    @RequestParam("pageNumber") int pageNumber,
                                                                    @RequestParam("pageSize") int pageSize) {
        //todo pagination https://youtu.be/oq-c3D67WqM?t=1931
        return null;
    }

    /**
     * Get all orders.
     *
     * @param token      JWT token.
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
    public Page<OrderResponseDTO> getAllOrders(@RequestHeader("Authorization") String token,
                                                               @ParameterObject Pageable pageable) {
        //todo pagination https://youtu.be/oq-c3D67WqM?t=1931
        return null;
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
        return null;
    }


    /**
     * SERVICE ENDPOINT.
     *
     * @param serviceToken JWT token.
     * @param orderId     order id.
     * @param status     new status.
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
        return null;
    }

}
