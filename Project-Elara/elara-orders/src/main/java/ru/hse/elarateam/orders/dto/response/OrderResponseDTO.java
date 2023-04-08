package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.model.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrderResponseDTO {
    private UUID id;
    private UUID userId;
    private List<OrderedItemResponseDTO> positions;
    private BigDecimal totalWithDiscount;
    private BigDecimal totalWithoutDiscount;
    private ShipmentDetailsResponseDTO shipmentDetails;
    private PaymentDetailsInfoDTO paymentDetails;
    private OrderStatus status;
    private Integer totalHeight;
    private Integer totalLength;
    private Integer totalWidth;
    private Integer totalWeight;
}
