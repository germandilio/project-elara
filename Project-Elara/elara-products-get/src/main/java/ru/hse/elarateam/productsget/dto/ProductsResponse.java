package ru.hse.elarateam.productsget.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductsResponse {
    private Page<ProductInfoDTO> products;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private List<String> presentedBrands;

    private List<String> presentedCountries;

    private List<String> presentedSports;

    private List<String> presentedColors;

    private List<String> presentedFeatures;

    private List<Double> presentedSizeUS;

    private List<Double> presentedSizeEUR;

    private List<Double> presentedSizeUK;
}
