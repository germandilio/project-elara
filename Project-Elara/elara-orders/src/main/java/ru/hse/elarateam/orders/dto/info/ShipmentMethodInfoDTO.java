package ru.hse.elarateam.orders.dto.info;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShipmentMethodInfoDTO {
    private Integer tariffCode;
    private String tariffName;
    private String tariffDescription;
    private Integer deliveryMode;
    private BigDecimal deliverySum;
    private Integer periodMin;
    private Integer periodMax;
}
