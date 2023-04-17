package ru.hse.elarateam.orders.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ProductResponseDTO {
    private UUID productId;
    private BigDecimal price;
    private Integer discount;
    private Long quantity;
}
