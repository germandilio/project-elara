package ru.hse.elarateam.productsget.web.repository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.productsget.dto.PriceRangeDTO;
import ru.hse.elarateam.productsget.model.Product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    @NonNull
    @Override
    Optional<Product> findById(@NonNull UUID uuid);

    @NonNull
    @Override
    Page<Product> findAll(@NonNull Pageable pageable);

    @Operation(description = "Find products by ids")
    Page<Product> findAllByIdIn(Collection<UUID> uuids, Pageable pageable);

    Page<Product> findAllByUpcIn(Collection<String> upcs, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = """
                SELECT MIN(p.price) AS min, MAX(p.price) AS max FROM products p
                LEFT JOIN products_colors pc ON pc.product_id = p.id
                LEFT JOIN colors c ON pc.colors_id = c.id
                JOIN products_features pf on p.id = pf.product_id
                JOIN features f on f.id = pf.features_id
                JOIN products_sports ps on p.id = ps.product_id
                JOIN sports s on ps.sports_id = s.id
                 WHERE (:sports IS NULL OR s.name IN :sports) AND
                        (:colors IS NULL OR c.name IN :colors) AND
                         (:features IS NULL OR f.name IN :features) AND
                          (:countries IS NULL OR p.country_of_origin IN :countries) AND
                           (:brands IS NULL OR p.brand IN :brands) AND
                            (:sizeUS IS NULL OR p.sizeus IN :sizeUS) AND
                             (:sizeUK IS NULL OR p.sizeuk IN :sizeUK) AND
                              (:sizeEUR IS NULL OR p.sizeeur IN :sizeEUR) AND
                               (:query IS NULL OR p.name LIKE CONCAT('%', :query, '%') OR c.name LIKE CONCAT('%', :query, '%'))
            """, nativeQuery = true)
    PriceRangeDTO findPriceRange(Collection<String> sports,
                                 Collection<String> colors,
                                 Collection<String> features,
                                 Collection<String> countries,
                                 Collection<String> brands,
                                 Collection<Double> sizeUS,
                                 Collection<Double> sizeEUR,
                                 Collection<Double> sizeUK,
                                 String query);

    default PriceRangeDTO findPriceRangeNullable(Collection<String> sports,
                                                 Collection<String> colors,
                                                 Collection<String> features,
                                                 Collection<String> countries,
                                                 Collection<String> brands,
                                                 Collection<Double> sizeUS,
                                                 Collection<Double> sizeEUR,
                                                 Collection<Double> sizeUK,
                                                 String query) {
        // verification part
        Collection<String> prSports = sports == null ? Collections.emptyList() : sports;
        Collection<String> prColors = colors == null ? Collections.emptyList() : colors;
        Collection<String> prFeatures = features == null ? Collections.emptyList() : features;
        Collection<String> prCountries = countries == null ? Collections.emptyList() : countries;
        Collection<String> prBrands = brands == null ? Collections.emptyList() : brands;
        Collection<Double> prSizeUS = sizeUS == null ? Collections.emptyList() : sizeUS;
        Collection<Double> prSizeEUR = sizeEUR == null ? Collections.emptyList() : sizeEUR;
        Collection<Double> prSizeUK = sizeUK == null ? Collections.emptyList() : sizeUK;
        String prQuery = query == null ? "" : query;
        return findPriceRange(prSports, prColors, prFeatures, prCountries, prBrands, prSizeUS, prSizeEUR, prSizeUK, prQuery);
    }

    Page<Product> findAllBySports_NameInAndColors_NameInAndFeatures_NameInAndCountryOfOriginInAndBrandInAndSizeUSInAndSizeEURInAndSizeUKInAndPriceBetweenAndNameContainingIgnoreCase(
            Collection<String> sports,
            Collection<String> colors,
            Collection<String> features,
            Collection<String> countries,
            Collection<String> brands,
            Collection<Double> sizeUS,
            Collection<Double> sizeEUR,
            Collection<Double> sizeUK,
            @NotNull BigDecimal minPrice,
            @NotNull BigDecimal maxPrice,
            String query,
            Pageable pageable);

    default Page<Product> findAllByFiltersAndQueryNullable(
            Collection<String> sports,
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
        // verification part
        Collection<String> prSports = sports == null ? Collections.emptyList() : sports;
        Collection<String> prColors = colors == null ? Collections.emptyList() : colors;
        Collection<String> prFeatures = features == null ? Collections.emptyList() : features;
        Collection<String> prCountries = countries == null ? Collections.emptyList() : countries;
        Collection<String> prBrands = brands == null ? Collections.emptyList() : brands;
        Collection<Double> prSizeUS = sizeUS == null ? Collections.emptyList() : sizeUS;
        Collection<Double> prSizeEUR = sizeEUR == null ? Collections.emptyList() : sizeEUR;
        Collection<Double> prSizeUK = sizeUK == null ? Collections.emptyList() : sizeUK;
        BigDecimal prMinPrice = minPrice == null ? BigDecimal.ZERO : minPrice;
        BigDecimal prMaxPrice = maxPrice == null ? BigDecimal.valueOf(Long.MAX_VALUE) : maxPrice;
        String prQuery = query == null ? "" : query;

        return findAllBySports_NameInAndColors_NameInAndFeatures_NameInAndCountryOfOriginInAndBrandInAndSizeUSInAndSizeEURInAndSizeUKInAndPriceBetweenAndNameContainingIgnoreCase(
                prSports,
                prColors,
                prFeatures,
                prCountries,
                prBrands,
                prSizeUS,
                prSizeEUR,
                prSizeUK,
                prMinPrice,
                prMaxPrice,
                prQuery,
                pageable
        );
    }
}
