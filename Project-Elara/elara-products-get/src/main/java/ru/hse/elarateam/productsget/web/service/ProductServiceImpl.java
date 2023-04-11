package ru.hse.elarateam.productsget.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.productsget.dto.*;
import ru.hse.elarateam.productsget.web.mappers.ColorMapper;
import ru.hse.elarateam.productsget.web.mappers.FeatureMapper;
import ru.hse.elarateam.productsget.web.mappers.ProductsMapper;
import ru.hse.elarateam.productsget.web.mappers.SportMapper;
import ru.hse.elarateam.productsget.web.repository.ColorsRepository;
import ru.hse.elarateam.productsget.web.repository.FeatureRepository;
import ru.hse.elarateam.productsget.web.repository.ProductsRepository;
import ru.hse.elarateam.productsget.web.repository.SportsRepository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductsRepository productRepository;
    private final FeatureRepository featureRepository;
    private final ColorsRepository colorsRepository;
    private final SportsRepository sportsRepository;

    private final ProductsMapper productsMapper;

    private final FeatureMapper featureMapper;

    private final ColorMapper colorMapper;

    private final SportMapper sportMapper;

    @Transactional(readOnly = true)
    @Override
    public ProductInfoDTO getProductInfo(UUID id) {
        return productsMapper.productToProductInfoDTO(productRepository.findById(id).orElse(null));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> getAllProductsByIds(Collection<UUID> ids, Pageable pageable) {
        return productRepository.findAllByIdIn(ids, pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> getAllProductsByUPCs(Collection<String> upcs, Pageable pageable) {
        return productRepository.findAllByUpcIn(upcs, pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> getProductsByNames(String name, Pageable pageable) {
        return productRepository.findAllByNameContainingIgnoreCase(name, pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> finaAllByFiltersAndQuery(Collection<String> sports,
                                                         Collection<String> colors,
                                                         Collection<String> features,
                                                         Collection<String> countries,
                                                         Collection<String> brands,
                                                         Collection<Double> sizeUS,
                                                         Collection<Double> sizeEUR,
                                                         Collection<Double> sizeUK,
                                                         BigDecimal minPrice,
                                                         BigDecimal maxPrice,
                                                         String query,
                                                         Pageable pageable) {
        log.trace("finaAllByFiltersAndQuery({}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {})", sports, colors, features,
                countries, brands, sizeUS, sizeEUR, sizeUK, minPrice, maxPrice, query);

        return productRepository.findAllByFiltersAndQueryNullable(
                        sports, colors, features, countries, brands, sizeUS, sizeEUR, sizeUK, minPrice, maxPrice, query, pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ProductInfoDTO> findAllRecentAddedProducts(Pageable pageable) {
        return productRepository.findAllByOrderByCreatedDateDesc(pageable)
                .map(productsMapper::productToProductInfoDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public PriceRangeDTO getPriceRange() {
        return PriceRangeDTO.builder()
                .min(productRepository.findMinPrice())
                .max(productRepository.findMaxPrice())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public ColorInfoDTO getColor(Long id) {
        return colorsRepository.findById(id)
                .map(colorMapper::colorToColorInfoDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ColorInfoDTO> getAllColors() {
        return colorsRepository.findAll().stream()
                .map(colorMapper::colorToColorInfoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public FeatureInfoDTO getFeature(Long id) {
        return featureRepository.findById(id)
                .map(featureMapper::featureToFeatureInfoDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FeatureInfoDTO> getAllFeatures() {
        return featureRepository.findAll().stream()
                .map(featureMapper::featureToFeatureInfoDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public SportInfoDTO getSport(Long id) {
        return sportsRepository.findById(id)
                .map(sportMapper::sportToSportInfoDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SportInfoDTO> getAllSports() {
        return sportsRepository.findAll().stream()
                .map(sportMapper::sportToSportInfoDTO)
                .toList();
    }
}
