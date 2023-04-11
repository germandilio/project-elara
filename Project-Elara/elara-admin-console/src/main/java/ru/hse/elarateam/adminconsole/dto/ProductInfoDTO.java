package ru.hse.elarateam.adminconsole.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductInfoDTO {
    private UUID id;
    private String upc;

    private String name;
    private BigDecimal price;
    private Integer discount;
    private Integer quantity;

    private String description;
    private String brand;
    private String countryOfOrigin;

    private Double sizeUS;
    private Double sizeEUR;
    private Double sizeUK;

    private Set<SportInfoDTO> sports;
    private Set<ColorInfoDTO> colors;
    private Set<FeatureInfoDTO> features;
    private Set<String> pictures;

    private Double height;
    private Double width;
    private Double length;
    private Double weight;
}
