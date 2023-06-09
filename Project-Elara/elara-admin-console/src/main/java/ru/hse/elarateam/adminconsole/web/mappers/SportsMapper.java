package ru.hse.elarateam.adminconsole.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.adminconsole.dto.SportInfoDTO;
import ru.hse.elarateam.adminconsole.model.Sport;

import java.util.Set;

@Mapper
public interface SportsMapper {
    Sport sportInfoDTOToSport(SportInfoDTO sportInfoDTO);

    SportInfoDTO sportToSportInfoDTO(Sport sport);

    Set<SportInfoDTO> map(Set<Sport> sports);
}
