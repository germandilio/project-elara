package ru.hse.elarateam.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderRequestDTO {

    private UUID userId;

    private List<OrderedItemRequestDTO> positions;
}
