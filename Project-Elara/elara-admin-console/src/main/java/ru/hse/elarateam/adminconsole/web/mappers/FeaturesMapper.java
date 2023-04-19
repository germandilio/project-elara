package ru.hse.elarateam.adminconsole.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.model.Color;
import ru.hse.elarateam.adminconsole.model.Feature;
import ru.hse.elarateam.adminconsole.dto.FeatureInfoDTO;

import java.util.Set;

@Mapper
public interface FeaturesMapper {
    Feature featureInfoDTOToFeature(FeatureInfoDTO featureInfoDTO);

    FeatureInfoDTO featureToFeatureInfoDTO(Feature feature);

    Set<FeatureInfoDTO> map(Set<Feature> features);
}
