package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.model.Product;

@Mapper(uses = {ColorsMapper.class, FeaturesMapper.class, SportsMapper.class})
public interface ProductsMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Product productInfoDTOToProduct(ProductInfoDTO productDTO);

    ProductInfoDTO productToProductInfoDTO(Product product);
}
