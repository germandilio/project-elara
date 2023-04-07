package ru.hse.elarateam.orders.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderedItemRequestDTO {
    private UUID productId;
    private Long quantity;
}
