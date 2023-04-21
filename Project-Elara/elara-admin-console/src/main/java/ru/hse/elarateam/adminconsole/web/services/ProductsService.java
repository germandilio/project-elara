package ru.hse.elarateam.adminconsole.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.model.Product;
import ru.hse.elarateam.adminconsole.web.mappers.ProductsMapper;
import ru.hse.elarateam.adminconsole.web.repositories.ProductsRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {
    // todo add logs

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    public ProductInfoDTO createProduct(ProductInfoDTO productInfoDTO) {
        if (productInfoDTO.getId() != null) {
            throw new RuntimeException("New product id must be null.");
        }

        var saved = productsRepository.saveAndFlush(
                productsMapper.productInfoDTOToProduct(productInfoDTO));
        return productsMapper.productToProductInfoDTO(saved);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public ProductInfoDTO updateProduct(ProductInfoDTO productInfoDTO) {
        if (!productsRepository.existsById(productInfoDTO.getId()))
            throw new RuntimeException("Product not found.");

        // todo check null colors
        var product = productsRepository.saveAndFlush(
                productsMapper.productInfoDTOToProduct(productInfoDTO));

        return productsMapper.productToProductInfoDTO(product);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteProduct(UUID productId) {
        if (!productsRepository.existsById(productId))
            throw new RuntimeException("Product not found.");

        productsRepository.deleteById(productId);
    }

    @Transactional(readOnly = true)
    public Product getProductById(UUID productId) {
        return productsRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));
    }
}
