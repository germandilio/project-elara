package ru.hse.elarateam.adminconsole.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.mappers.ColorsMapper;
import ru.hse.elarateam.adminconsole.repositories.ColorsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorsService {
    // todo add logs

    private final ColorsRepository colorsRepository;
    private final ColorsMapper colorsMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public ColorInfoDTO createColor(ColorInfoDTO colorInfoDTO) {
        if (colorInfoDTO.getId() != null) {
            throw new RuntimeException("New color id must be null.");
        }

        var saved = colorsRepository.saveAndFlush(
                colorsMapper.colorInfoDTOToColor(colorInfoDTO));


        return colorsMapper.colorToColorInfoDTO(saved);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public ColorInfoDTO updateColor(ColorInfoDTO colorInfoDTO) {
        if(!colorsRepository.existsById(colorInfoDTO.getId()))
            throw new RuntimeException("Color not found.");

        var color = colorsRepository.saveAndFlush(
                colorsMapper.colorInfoDTOToColor(colorInfoDTO));

        return colorsMapper.colorToColorInfoDTO(color);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteColor(Long colorId) {
        if(!colorsRepository.existsById(colorId))
            throw new RuntimeException("Color not found.");

        colorsRepository.deleteById(colorId);
    }
}
