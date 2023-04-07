package ru.hse.elarateam.orders.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ShipmentMethodResponseDTO {
    private Integer tariffCode;
    private String tariffName;
    private String tariffDescription;
    private Integer deliveryMode;
    private BigDecimal deliverySum;
    private Integer periodMin;
    private Integer periodMax;
}
