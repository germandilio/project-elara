package ru.hse.elarateam.productsget.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.hse.elarateam.productsget.dto.ProductInfoDTO;
import ru.hse.elarateam.productsget.model.Product;

@Mapper
public interface ProductsMapper {
    SportMapper sportMapper = Mappers.getMapper(SportMapper.class);
    ColorMapper colorMapper = Mappers.getMapper(ColorMapper.class);
    FeatureMapper featureMapper = Mappers.getMapper(FeatureMapper.class);

    @Mapping(target = "sports", expression = "java(product.getSports().stream().map(sportMapper::sportToSportInfoDTO).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "colors", expression = "java(product.getColors().stream().map(colorMapper::colorToColorInfoDTO).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "features", expression = "java(product.getFeatures().stream().map(featureMapper::featureToFeatureInfoDTO).collect(java.util.stream.Collectors.toSet()))")
    ProductInfoDTO productToProductInfoDTO(Product product);
}
