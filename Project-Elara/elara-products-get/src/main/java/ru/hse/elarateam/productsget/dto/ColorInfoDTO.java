package ru.hse.elarateam.productsget.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColorInfoDTO {
    private Long id;
    private String name;
    private String hex;
}
