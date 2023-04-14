package ru.hse.elarateam.adminconsole.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.model.Product;
import ru.hse.elarateam.adminconsole.repositories.ProductsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public ProductInfoDTO createProduct(ProductInfoDTO productInfoDTO) {
        // todo checks
        var saved = productsRepository.save(
                Product.builder()
                .name(productInfoDTO.getName())
                        .colors(productInfoDTO.getColors().forEach();)
                .build());
        return ProductInfoDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .description(saved.getDescription())
                .price(saved.getPrice())
                .build();
    }

}
