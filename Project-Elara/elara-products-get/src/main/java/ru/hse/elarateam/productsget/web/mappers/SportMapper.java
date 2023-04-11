package ru.hse.elarateam.productsget.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.productsget.dto.SportInfoDTO;
import ru.hse.elarateam.productsget.model.Sport;

@Mapper
public interface SportMapper {
    SportInfoDTO sportToSportInfoDTO(Sport sport);
}
