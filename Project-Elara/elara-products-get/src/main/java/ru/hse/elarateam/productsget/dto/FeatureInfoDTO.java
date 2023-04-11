package ru.hse.elarateam.productsget.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FeatureInfoDTO {
    private Long id;
    private String name;
    private String description;
}
