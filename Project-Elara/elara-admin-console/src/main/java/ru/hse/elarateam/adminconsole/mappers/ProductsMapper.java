package ru.hse.elarateam.adminconsole.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.model.Product;

@Mapper
public interface ProductsMapper {
    Product productInfoDTOToProduct(ProductInfoDTO productDTO);

    ProductInfoDTO productToProductInfoDTO(Product product);
}
