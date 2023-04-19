package ru.hse.elarateam.orders.dto.info;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ShipmentMethodInfoDTO {
    private UUID id;
    private Integer tariffCode;
    private String tariffName;
    private String tariffDescription;
    private Integer deliveryMode;
    private BigDecimal deliverySum;
    private Integer periodMin;
    private Integer periodMax;
}
