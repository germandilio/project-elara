package ru.hse.elarateam.productsget.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.productsget.dto.FeatureInfoDTO;
import ru.hse.elarateam.productsget.model.Feature;

@Mapper
public interface FeatureMapper {
    FeatureInfoDTO featureToFeatureInfoDTO(Feature feature);
}
