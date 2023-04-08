package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderedItemResponseDTO {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private BigDecimal price;
    private Integer discount;
    private Integer quantity;
}
