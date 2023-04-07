package ru.hse.elarateam.orders.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;

import java.util.UUID;

@RestController
@RequestMapping("/v1/orders")
public class OrdersController {

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return null;
    }

    @PostMapping("/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@RequestParam("orderId") UUID orderId) {
        return null;
    }

    @GetMapping("/get")
    public ResponseEntity<OrderResponseDTO> getOrderById(@RequestParam("orderId") UUID orderId) {
        return null;
    }

    @GetMapping("/get-by-user")
    public ResponseEntity<Page<OrderResponseDTO>> getOrdersByUserId(@RequestParam("pageNumber") int pageNumber,
                                                                    @RequestParam("pageSize") int pageSize,
                                                                    @RequestParam("userId") UUID userId) {
        //todo pagination https://youtu.be/oq-c3D67WqM?t=1931
        return null;
    }

    @PostMapping("/change-status")
    public ResponseEntity<OrderResponseDTO> changeOrderStatus(@RequestParam("orderId") UUID orderId,
                                                              @RequestParam("status") String status) {
        return null;
    }

}
