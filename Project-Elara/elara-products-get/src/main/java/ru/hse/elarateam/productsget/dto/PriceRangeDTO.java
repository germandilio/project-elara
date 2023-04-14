package ru.hse.elarateam.productsget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class PriceRangeDTO {
    private BigDecimal min;
    private BigDecimal max;
}
