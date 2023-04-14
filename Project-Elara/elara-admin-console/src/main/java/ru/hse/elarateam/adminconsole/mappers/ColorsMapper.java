package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.model.Color;

@Mapper
public interface ColorsMapper {
    Color colorInfoDTOToColor(ColorInfoDTO colorInfoDTO);

    ColorInfoDTO colorToColorInfoDTO(Color color);
}
