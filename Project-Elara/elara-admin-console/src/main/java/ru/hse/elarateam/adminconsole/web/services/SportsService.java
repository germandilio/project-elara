package ru.hse.elarateam.adminconsole.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.dto.SportInfoDTO;
import ru.hse.elarateam.adminconsole.web.mappers.SportsMapper;
import ru.hse.elarateam.adminconsole.web.repositories.SportsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SportsService {
    private final SportsRepository sportsRepository;
    private final SportsMapper sportsMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public SportInfoDTO createSport(SportInfoDTO sportInfoDTO) {
        if (sportInfoDTO.getId() != null) {
            throw new RuntimeException("New sport id must be null.");
        }

        var sport = sportsRepository.saveAndFlush(
                sportsMapper.sportInfoDTOToSport(sportInfoDTO));

        return sportsMapper.sportToSportInfoDTO(sport);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public SportInfoDTO updateSport(SportInfoDTO sportInfoDTO) {
        if (!sportsRepository.existsById(sportInfoDTO.getId()))
            throw new RuntimeException("Sport not found.");

        var sport = sportsRepository.saveAndFlush(
                sportsMapper.sportInfoDTOToSport(sportInfoDTO));

        return sportsMapper.sportToSportInfoDTO(sport);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteSport(Long sportId) {
        if (!sportsRepository.existsById(sportId))
            throw new RuntimeException("Sport not found.");

        sportsRepository.deleteById(sportId);
    }
}
