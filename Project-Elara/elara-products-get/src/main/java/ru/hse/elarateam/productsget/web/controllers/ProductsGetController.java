package ru.hse.elarateam.productsget.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.productsget.dto.PriceRangeDTO;
import ru.hse.elarateam.productsget.dto.ProductInfoDTO;
import ru.hse.elarateam.productsget.dto.ProductsResponse;
import ru.hse.elarateam.productsget.web.service.ProductService;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/products")
@RestController
public class ProductsGetController {
    private final ProductService productService;

    /**
     * Get product by id
     *
     * @param id product id
     * @return product info
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductInfoDTO> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductInfo(id));
    }

    /**
     * Get products by ids or upcs or name
     * Note: If ids is present, then upcs and name are ignored etc.
     *
     * @param ids      product ids
     * @param upcs     product Universal Product Codes
     * @param name     product name (will find all products with name containing this string)
     * @param pageable page info
     * @return page of products
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/service")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductInfoDTO> getAllProducts(@RequestParam(required = false) Optional<UUID[]> ids,
                                               @RequestParam(required = false) Optional<String[]> upcs,
                                               @RequestParam(required = false) Optional<String> name,
                                               @ParameterObject Pageable pageable) {
        if (ids.isPresent()) {
            return productService.getAllProductsByIds(List.of(ids.get()), pageable);
        } else if (upcs.isPresent()) {
            return productService.getAllProductsByUPCs(List.of(upcs.get()), pageable);
        } else if (name.isPresent()) {
            return productService.getProductsByNames(name.get(), pageable);
        } else {
            return productService.getAllProducts(pageable);
        }
    }

    /**
     * Get all brands
     *
     * @return list of brands
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/brands")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllBrands() {
        return productService.getAllBrands();
    }

    /**
     * Get all countries
     *
     * @return list of countries
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/countries")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getAllCountries() {
        return productService.getAllCountries();
    }

    /**
     * Get all sizes (US)
     *
     * @return list of sizes
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/sizes/us")
    @ResponseStatus(HttpStatus.OK)
    public List<Double> getAllSizesUS() {
        return productService.getAllSizeUS();
    }

    /**
     * Get all sizes (EUR)
     *
     * @return list of sizes
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/sizes/eur")
    @ResponseStatus(HttpStatus.OK)
    public List<Double> getAllSizesEUR() {
        return productService.getAllSizeEUR();
    }

    /**
     * Get all sizes (UK)
     *
     * @return list of sizes
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/sizes/uk")
    @ResponseStatus(HttpStatus.OK)
    public List<Double> getAllSizesUK() {
        return productService.getAllSizeUK();
    }


    /**
     * Get products by filters and search query.
     * All parameters are optional.
     * <p>
     * Note: if filters are present, query will find products that match all specified filters.
     * Search query will run over name only.
     *
     * @param sports    sports, can be multiple
     * @param colors    colors, can be multiple
     * @param features  features, can be multiple
     * @param countries countries, can be multiple
     * @param brands    brands, can be multiple
     * @param sizeUS    sizeUS, can be multiple
     * @param sizeEUR   sizeEUR, can be multiple
     * @param sizeUK    sizeUK, can be multiple
     * @param minPrice  min price (should be less or equal than maxPrice)
     * @param maxPrice  max price (should be greater or equal than minPrice)
     * @param query     search query
     * @param pageable  page info
     * @return page of products
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ProductsResponse getAllProductsByFilers(@RequestParam(required = false) Collection<String> sports,
                                                   @RequestParam(required = false) Collection<String> colors,
                                                   @RequestParam(required = false) Collection<String> features,
                                                   @RequestParam(required = false) Collection<String> countries,
                                                   @RequestParam(required = false) Collection<String> brands,
                                                   @RequestParam(required = false) Collection<Double> sizeUS,
                                                   @RequestParam(required = false) Collection<Double> sizeEUR,
                                                   @RequestParam(required = false) Collection<Double> sizeUK,
                                                   @RequestParam(required = false) BigDecimal minPrice,
                                                   @RequestParam(required = false) BigDecimal maxPrice,
                                                   @RequestParam(required = false) String query,
                                                   @ParameterObject Pageable pageable) {
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0)
            throw new IllegalArgumentException("minPrice must be less than maxPrice");

        return productService.finaAllByFiltersAndQuery(
                sports, colors, features, countries, brands,
                sizeUS, sizeEUR, sizeUK,
                minPrice, maxPrice,
                query,
                pageable);
    }

    /**
     * Get products that were added recently.
     *
     * @param pageable page info
     * @return page of products
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/recent")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductInfoDTO> getAllRecentProducts(@ParameterObject Pageable pageable) {
        return productService.findAllRecentAddedProducts(pageable);
    }

    /**
     * Get price range for products that match filters and search query.
     *
     * @param sports    sports, can be multiple
     * @param colors    colors, can be multiple
     * @param features  features, can be multiple
     * @param countries countries, can be multiple
     * @param brands    brands, can be multiple
     * @param sizeUS    sizeUS, can be multiple
     * @param sizeEUR   sizeEUR, can be multiple
     * @param sizeUK    sizeUK, can be multiple
     * @param query     search query
     * @return price range (min and max), if no products found - min and max will be null
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/price-range")
    public ResponseEntity<PriceRangeDTO> getPriceRange(@RequestParam(required = false) Collection<String> sports,
                                                       @RequestParam(required = false) Collection<String> colors,
                                                       @RequestParam(required = false) Collection<String> features,
                                                       @RequestParam(required = false) Collection<String> countries,
                                                       @RequestParam(required = false) Collection<String> brands,
                                                       @RequestParam(required = false) Collection<Double> sizeUS,
                                                       @RequestParam(required = false) Collection<Double> sizeEUR,
                                                       @RequestParam(required = false) Collection<Double> sizeUK,
                                                       @RequestParam(required = false) String query) {
        return ResponseEntity.ok(productService.getPriceRange(sports, colors, features, countries, brands, sizeUS, sizeEUR, sizeUK, query));
    }
}
