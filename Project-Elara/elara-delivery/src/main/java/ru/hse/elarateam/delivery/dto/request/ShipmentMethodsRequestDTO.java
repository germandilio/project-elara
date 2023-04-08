package ru.hse.elarateam.delivery.dto.request;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;

import java.util.UUID;

@Data
@Builder
public class ShipmentMethodsRequestDTO {
    private UUID orderId;
    private AddressInfoDTO toAddress;
}
