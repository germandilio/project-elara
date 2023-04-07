package ru.hse.elarateam.products.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.products.dto.request.OrderRequestDTO;
import ru.hse.elarateam.products.dto.response.ResponsePayloadDTO;
import ru.hse.elarateam.products.services.ProductsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping("/allocate")
    public ResponseEntity<ResponsePayloadDTO<List<UUID>>> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        // todo доделать
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // аллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var allocatedProducts = productsService.allocateProducts(requiredProducts);
        return new ResponseEntity<>(
                new ResponsePayloadDTO<>("Products allocated.", allocatedProducts),
                HttpStatus.OK);
    }

    @PostMapping("/deallocate")
    public ResponseEntity<ResponsePayloadDTO<List<UUID>>> deallocateProducts(@RequestBody OrderRequestDTO orderRequestDTO) {
        // todo доделать
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // деаллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var deallocatedProducts = productsService.deallocateProducts(requiredProducts);
        return new ResponseEntity<>(
                new ResponsePayloadDTO<>("Products deallocated.", deallocatedProducts),
                HttpStatus.OK);
    }
}
