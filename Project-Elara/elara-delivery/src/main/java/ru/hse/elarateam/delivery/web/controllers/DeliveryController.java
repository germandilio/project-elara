package ru.hse.elarateam.delivery.web.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;
import ru.hse.elarateam.delivery.dto.request.SelectShipmentMethodRequestDTO;
import ru.hse.elarateam.delivery.dto.request.ShipmentMethodsRequestDTO;
import ru.hse.elarateam.delivery.dto.response.ShipmentMethodsResponseDTO;

import java.util.UUID;

@RestController
@RequestMapping("/v1/delivery")
public class DeliveryController {

    @PostMapping("/get")
    public ResponseEntity<ShipmentMethodsResponseDTO> getShipmentMethods(@RequestBody ShipmentMethodsRequestDTO shipmentMethodsRequestDTO) {
        return null;
    }

    // todo подумать над типом возврата
    @PostMapping("/select")
    public ResponseEntity<?> selectShipmentMethod(@RequestBody SelectShipmentMethodRequestDTO selectShipmentMethodRequestDTO) {
        return null;
    }

    @GetMapping("/get-saved-addresses")
    public ResponseEntity<Page<AddressInfoDTO>> getSavedAddresses(@RequestParam("pageNumber") int pageNumber,
                                                                  @RequestParam("pageSize") int pageSize,
                                                                  @RequestParam("userId") UUID userId) {
        //todo pagination https://youtu.be/oq-c3D67WqM?t=1931
        return null;
    }


}
