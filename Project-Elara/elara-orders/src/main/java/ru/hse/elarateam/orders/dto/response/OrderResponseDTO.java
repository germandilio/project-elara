package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.dto.info.ShipmentDetailsInfoDTO;
import ru.hse.elarateam.orders.model.status.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class OrderResponseDTO {
    private UUID id;
    private UUID userId;
    private List<OrderedItemResponseDTO> positions;
    private BigDecimal totalWithDiscount;
    private BigDecimal totalWithoutDiscount;
    private ShipmentDetailsInfoDTO shipmentDetails;
    private PaymentDetailsInfoDTO paymentDetails;
    private OrderStatus status;
    private Double totalHeight;
    private Double totalLength;
    private Double totalWidth;
    private Double totalWeight;
}
