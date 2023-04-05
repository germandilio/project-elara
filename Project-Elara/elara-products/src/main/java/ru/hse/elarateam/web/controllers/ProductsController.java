package ru.hse.elarateam.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.dto.request.OrderRequestDTO;
import ru.hse.elarateam.services.ProductsService;

@RequestMapping("/v1/products/")
@RestController
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping("allocate")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        // eсли да то да иначе нет
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // аллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        try {
            return new ResponseEntity<>("Products allocated: " + productsService.allocateProducts(requiredProducts), HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Allocation failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("deallocate")
    public ResponseEntity<?> deallocateProducts(@RequestBody OrderRequestDTO orderRequestDTO) {
        // eсли да то да иначе нет
        // проверка jwt token'a, получение userId
        // проверка соответствия userId в запросе с полученным
        // деаллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        try {
            return new ResponseEntity<>("Products deallocated: " + productsService.deallocateProducts(requiredProducts), HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>("Deallocation failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
