package ru.hse.elarateam.orders.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

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
