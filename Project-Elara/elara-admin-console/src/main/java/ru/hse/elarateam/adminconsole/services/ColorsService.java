package ru.hse.elarateam.adminconsole.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.model.Color;
import ru.hse.elarateam.adminconsole.repositories.ColorsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorsService {
    private final ColorsRepository colorsRepository;

    public ColorInfoDTO createColor(ColorInfoDTO colorInfoDTO) {
        // todo checks
        var saved = colorsRepository.save(
                Color.builder()
                .name(colorInfoDTO.getName())
                .hex(colorInfoDTO.getHex())
                .build());
        return ColorInfoDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .hex(saved.getHex())
                .build();
    }
}
