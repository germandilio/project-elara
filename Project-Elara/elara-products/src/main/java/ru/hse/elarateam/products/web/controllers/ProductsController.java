package ru.hse.elarateam.products.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.products.dto.request.OrderRequestDTO;
import ru.hse.elarateam.products.dto.response.ResponsePayloadDTO;
import ru.hse.elarateam.products.services.ProductsService;
import ru.hse.elarateam.products.services.jwt.service.ServiceTokenUtilsImpl;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    private final ServiceTokenUtilsImpl serviceTokenUtils;

    /**
     * SERVICE ENDPOINT.
     * Allocate products for order.
     *
     * @param serviceToken    JWT token.
     * @param orderRequestDTO order allocation request.
     * @return list of allocated products.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products allocated."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/allocate")
    public ResponseEntity<ResponsePayloadDTO<List<UUID>>> allocateProducts(@RequestHeader("Authorization") String serviceToken,
                                                                           @RequestBody OrderRequestDTO orderRequestDTO) {
        // service token validation
        if (!validateServiceToken(serviceToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // аллокация (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var allocatedProducts = productsService.allocateProducts(requiredProducts);
        return new ResponseEntity<>(
                new ResponsePayloadDTO<>("Products allocated.", allocatedProducts),
                HttpStatus.OK);
    }

    /**
     * SERVICE ENDPOINT.
     * Deallocate products for order.
     *
     * @param serviceToken    JWT token.
     * @param orderRequestDTO order deallocation request.
     * @return list of deallocated products.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products deallocated."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/deallocate")
    public ResponseEntity<ResponsePayloadDTO<List<UUID>>> deallocateProducts(@RequestHeader("Authorization") String serviceToken,
                                                                             @RequestBody OrderRequestDTO orderRequestDTO) {
        // service token validation
        if (!validateServiceToken(serviceToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // deallocation (transactional)
        var requiredProducts = orderRequestDTO.getPositions();
        var deallocatedProducts = productsService.deallocateProducts(requiredProducts);
        return new ResponseEntity<>(
                new ResponsePayloadDTO<>("Products deallocated.", deallocatedProducts),
                HttpStatus.OK);
    }

    private boolean validateServiceToken(String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer ")) {
            return false;
        }
        return serviceTokenUtils.validateToken(serviceToken.substring(7));
    }

}

