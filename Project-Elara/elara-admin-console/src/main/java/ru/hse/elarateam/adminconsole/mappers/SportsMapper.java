package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.adminconsole.dto.SportInfoDTO;
import ru.hse.elarateam.adminconsole.model.Sport;

@Mapper
public interface SportsMapper {
    @Mapping(target = "id", ignore = true)
    Sport sportInfoDTOToSport(SportInfoDTO sportInfoDTO);

    SportInfoDTO sportToSportInfoDTO(Sport sport);
}
