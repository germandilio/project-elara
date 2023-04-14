package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.adminconsole.model.Feature;
import ru.hse.elarateam.adminconsole.dto.FeatureInfoDTO;

@Mapper
public interface FeaturesMapper {
    Feature featureInfoDTOToFeature(FeatureInfoDTO featureInfoDTO);

    FeatureInfoDTO featureToFeatureInfoDTO(Feature feature);
}
