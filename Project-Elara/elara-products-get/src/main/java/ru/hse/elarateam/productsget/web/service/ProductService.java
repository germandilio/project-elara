package ru.hse.elarateam.productsget.web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.hse.elarateam.productsget.dto.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductInfoDTO getProductInfo(UUID id);

    Page<ProductInfoDTO> getAllProducts(Pageable pageable);

    Page<ProductInfoDTO> getAllProductsByIds(Collection<UUID> ids, Pageable pageable);

    Page<ProductInfoDTO> getAllProductsByUPCs(Collection<String> upcs, Pageable pageable);

    Page<ProductInfoDTO> getProductsByNames(String name, Pageable pageable);

    /**
     * Find all products by filters and query
     * @param sports
     * @param colors
     * @param features
     * @param countries
     * @param brands
     * @param sizeUS
     * @param sizeEUR
     * @param sizeUK
     * @param minPrice
     * @param maxPrice
     * @param query search query over name
     * @param pageable
     * @return page of products
     */
    Page<ProductInfoDTO> finaAllByFiltersAndQuery(Collection<String> sports,
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
                                                  Pageable pageable);

    /**
     * Find all products that were added recently
     * @param pageable
     * @return page of products
     */
    Page<ProductInfoDTO> findAllRecentAddedProducts(Pageable pageable);

    /**
     * Find price range of all products in store.
     * @return price range object.
     */
    PriceRangeDTO getPriceRange();

    ColorInfoDTO getColor(Long id);
    List<ColorInfoDTO> getAllColors();


    FeatureInfoDTO getFeature(Long id);
    List<FeatureInfoDTO> getAllFeatures();

    SportInfoDTO getSport(Long id);
    List<SportInfoDTO> getAllSports();
}
