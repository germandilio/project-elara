package ru.hse.products.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OrderedItemRequestDTO {

    private UUID productId;

    private Long quantity;
}
