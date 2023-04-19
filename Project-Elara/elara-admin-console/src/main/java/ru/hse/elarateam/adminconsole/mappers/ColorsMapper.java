package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.model.Color;

import java.util.Set;

@Mapper
public interface ColorsMapper {
    Color colorInfoDTOToColor(ColorInfoDTO colorInfoDTO);

    ColorInfoDTO colorToColorInfoDTO(Color color);

    Set<ColorInfoDTO> map(Set<Color> colors);
}
