package ru.hse.elarateam.products.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.products.dto.response.ProductResponseDTO;
import ru.hse.elarateam.products.model.Product;

@Mapper
public interface ProductsMapper {
    @Mapping(source = "id", target = "productId")
    @Mapping(target = "quantity", ignore = true)
    ProductResponseDTO productToProductResponseDTO(Product product);
}
