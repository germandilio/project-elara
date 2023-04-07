package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressResponseDTO {
    private UUID id;
    private String postalCode;
    private String city;
    private String country;
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String entranceNumber;
}
