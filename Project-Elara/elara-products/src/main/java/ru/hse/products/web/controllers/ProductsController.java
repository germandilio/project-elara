package ru.hse.products.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.products.dto.request.OrderRequestDTO;
import ru.hse.products.dto.response.ResponsePayloadDTO;
import ru.hse.products.services.ProductsService;

@RequestMapping("/v1/products")
@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping("/allocate")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        // todo доделать
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // аллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var allocatedProducts = productsService.allocateProducts(requiredProducts);
        return new ResponseEntity<>(ResponsePayloadDTO.builder()
                .message("Products allocated.")
                .data(allocatedProducts)
                .build(), HttpStatus.OK);
    }

    @PostMapping("/deallocate")
    public ResponseEntity<?> deallocateProducts(@RequestBody OrderRequestDTO orderRequestDTO) {
        // todo доделать
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // деаллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var deallocatedProducts = productsService.deallocateProducts(requiredProducts);
        return new ResponseEntity<>(ResponsePayloadDTO.builder()
                .message("Products deallocated.")
                .data(deallocatedProducts)
                .build(), HttpStatus.OK);
    }
}
