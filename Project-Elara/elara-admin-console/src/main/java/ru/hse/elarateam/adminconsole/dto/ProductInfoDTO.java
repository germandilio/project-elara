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
    private Integer upc;
    private String name;
    private BigDecimal price;
    private Integer discount;
    private String description;
    private String brand;
    private Integer quantity;
    private String countryOfOrigin;
    private Integer sizeUS;
    private Integer sizeEUR;
    private Integer sizeUK;
    private Set<SportInfoDTO> sports;
    private Set<ColorInfoDTO> colors;
    private Set<FeatureInfoDTO> features;
    private Set<String> pictures;
    private Integer height;
    private Integer width;
    private Integer length;
    private Integer weight;
}
