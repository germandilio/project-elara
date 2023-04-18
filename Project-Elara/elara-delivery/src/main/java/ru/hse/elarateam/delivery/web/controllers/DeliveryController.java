package ru.hse.elarateam.delivery.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;
import ru.hse.elarateam.delivery.dto.request.SelectShipmentMethodRequestDTO;
import ru.hse.elarateam.delivery.dto.request.ShipmentMethodsRequestDTO;
import ru.hse.elarateam.delivery.dto.response.ShipmentMethodsResponseDTO;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/delivery")
public class DeliveryController {

    // todo authentication
    /**
     * Get shipment methods by shipmentMethodsRequestDTO.
     *
     * @param shipmentMethodsRequestDTO shipment info.
     * @param token                     JWT token.
     * @return shipmentMethodsResponseDTO or string exception message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment methods were successfully received.",
                    content = @Content(schema = @Schema(implementation = ShipmentMethodsResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/get")
    public ResponseEntity<ShipmentMethodsResponseDTO> getShipmentMethods(@RequestHeader("Authorization") String token,
                                                                         @RequestBody ShipmentMethodsRequestDTO shipmentMethodsRequestDTO) {
        return null;
    }

    /**
     * Select shipment methods by selectShipmentMethodRequestDTO.
     *
     * @param selectShipmentMethodRequestDTO selected shipment method info.
     * @param token                          JWT token.
     * @return string message.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shipment method selected."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/select")
    public ResponseEntity<?> selectShipmentMethod(@RequestHeader("Authorization") String token,
                                                  @RequestBody SelectShipmentMethodRequestDTO selectShipmentMethodRequestDTO) {
        return null;
    }


    /**
     * Get users saved addresses. Paginated.
     *
     * @param token    JWT token.
     * @param userId   user id.
     * @return page of saved addresses.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/saved-addresses")
    public ResponseEntity<List<AddressInfoDTO>> getSavedAddresses(@RequestHeader("Authorization") String token,
                                                                  @RequestParam("userId") UUID userId) {
        // not to do pagination https://youtu.be/oq-c3D67WqM?t=1931
        return null;
    }


}
