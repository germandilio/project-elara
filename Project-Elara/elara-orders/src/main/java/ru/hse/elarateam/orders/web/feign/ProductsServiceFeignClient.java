package ru.hse.elarateam.orders.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.orders.configs.auth.ProductsServiceConfig;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.ProductResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Feign client for calling products service.
 */
@FeignClient(name = "elara-products", url = "http://localhost:8084", configuration = ProductsServiceConfig.class)
public interface ProductsServiceFeignClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/allocate", produces = "application/json")
    @ResponseBody
    ResponseEntity<List<ProductResponseDTO>> allocateProducts(@RequestBody OrderRequestDTO orderRequestDTO);

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/deallocate", produces = "application/json")
    @ResponseBody
    ResponseEntity<List<UUID>> deallocateProducts(@RequestBody OrderRequestDTO orderRequestDTO);
}