package ru.hse.elarateam.productsget.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.productsget.dto.ColorInfoDTO;
import ru.hse.elarateam.productsget.model.Color;

@Mapper
public interface ColorMapper {
    ColorInfoDTO colorToColorInfoDTO(Color color);
}
