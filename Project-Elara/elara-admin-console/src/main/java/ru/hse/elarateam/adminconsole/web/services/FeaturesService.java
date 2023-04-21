package ru.hse.elarateam.adminconsole.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.dto.FeatureInfoDTO;
import ru.hse.elarateam.adminconsole.web.mappers.FeaturesMapper;
import ru.hse.elarateam.adminconsole.web.repositories.FeaturesRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeaturesService {
    private final FeaturesRepository featuresRepository;
    private final FeaturesMapper featuresMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public FeatureInfoDTO createFeature(FeatureInfoDTO featureInfoDTO) {
        if (featureInfoDTO.getId() != null) {
            throw new RuntimeException("New feature id must be null.");
        }

        var saved = featuresRepository.saveAndFlush(
                featuresMapper.featureInfoDTOToFeature(featureInfoDTO));

        return featuresMapper.featureToFeatureInfoDTO(saved);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public FeatureInfoDTO updateFeature(FeatureInfoDTO featureInfoDTO) {
        if (!featuresRepository.existsById(featureInfoDTO.getId()))
            throw new RuntimeException("Feature not found.");

        var saved = featuresRepository.saveAndFlush(
                featuresMapper.featureInfoDTOToFeature(featureInfoDTO));

        return featuresMapper.featureToFeatureInfoDTO(saved);

    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteFeature(Long featureId) {
        if (!featuresRepository.existsById(featureId))
            throw new RuntimeException("Feature not found.");

        featuresRepository.deleteById(featureId);
    }
}
