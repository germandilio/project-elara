package ru.hse.elarateam.productsget.web.repository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
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
    Optional<Product> findById(@NonNull @RequestParam("id") UUID uuid);

    @NonNull
    @Override
    Page<Product> findAll(@NonNull Pageable pageable);

    @Operation(description = "Find products by ids")
    Page<Product> findAllByIdIn(@RequestParam("ids") Collection<UUID> uuids, Pageable pageable);

    Page<Product> findAllByUpcIn(@RequestParam("upcs") Collection<String> upcs, Pageable pageable);

    Page<Product> findAllByNameContainingIgnoreCase(@RequestParam("name") String name, Pageable pageable);

    Page<Product> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query("SELECT MIN(p.price) FROM Product p")
    BigDecimal findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p")
    BigDecimal findMaxPrice();

    Page<Product> findAllBySports_NameInAndColors_NameInAndFeatures_NameInAndCountryOfOriginInAndBrandInAndSizeUSInAndSizeEURInAndSizeUKInAndPriceBetweenAndNameContainingIgnoreCase(
            @RequestParam("sports") Collection<String> sports,
            @RequestParam("colors") Collection<String> colors,
            @RequestParam("features") Collection<String> features,
            @RequestParam("countries") Collection<String> countries,
            @RequestParam("brands") Collection<String> brands,
            @RequestParam("sizeUS") Collection<Double> sizeUS,
            @RequestParam("sizeEUR") Collection<Double> sizeEUR,
            @RequestParam("sizeUK") Collection<Double> sizeUK,
            @RequestParam("minPrice") @NotNull BigDecimal minPrice,
            @RequestParam("maxPrice") @NotNull BigDecimal maxPrice,
            @RequestParam("query") String query,
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
