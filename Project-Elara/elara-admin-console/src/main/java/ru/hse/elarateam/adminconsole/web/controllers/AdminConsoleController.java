package ru.hse.elarateam.adminconsole.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.dto.FeatureInfoDTO;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.dto.SportInfoDTO;
import ru.hse.elarateam.adminconsole.services.ColorsService;
import ru.hse.elarateam.adminconsole.services.FeaturesService;
import ru.hse.elarateam.adminconsole.services.ProductsService;
import ru.hse.elarateam.adminconsole.services.SportsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin-console")
public class AdminConsoleController {
    private final ProductsService productsService;
    private final ColorsService colorsService;
    private final FeaturesService featuresService;
    private final SportsService sportsService;

    // todo authoirzation

    /**
     * Create product.
     *
     * @param productInfoDTO id must be null.
     * @return created product.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created.",
                    content = @Content(schema = @Schema(implementation = ProductInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    // todo test create product with nonexistent color (id = null, id != null but color doesn't exist)
    // todo test adding new picture
    @PostMapping("/product")
    public ResponseEntity<ProductInfoDTO> createProduct(@RequestBody ProductInfoDTO productInfoDTO) {
        return ResponseEntity.ok(productsService.createProduct(productInfoDTO));
    }

    /**
     * Update product.
     *
     * @param productInfoDTO id must not be null.
     * @return updated product.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated.",
                    content = @Content(schema = @Schema(implementation = ProductInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    // todo test update product with nonexistent color (id = null, id != null but color doesn't exist)
    // todo test removing picture, adding new picture
    @PutMapping("/product")
    public ResponseEntity<ProductInfoDTO> updateProduct(@RequestBody ProductInfoDTO productInfoDTO) {
        return ResponseEntity.ok(productsService.updateProduct(productInfoDTO));
    }

    /**
     * Delete product.
     *
     * @param productId id of product to delete.
     * @return status code.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Product not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestParam("productID") UUID productId) {
        productsService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    /**
     * Create feature.
     *
     * @param featureInfoDTO id must be null.
     * @return created feature.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature created.",
                    content = @Content(schema = @Schema(implementation = FeatureInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> createFeature(@RequestBody FeatureInfoDTO featureInfoDTO) {
        return ResponseEntity.ok(featuresService.createFeature(featureInfoDTO));
    }

    /**
     * Update feature.
     *
     * @param featureInfoDTO id must not be null.
     * @return updated feature.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature updated.",
                    content = @Content(schema = @Schema(implementation = FeatureInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Feature not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> updateFeature(@RequestBody FeatureInfoDTO featureInfoDTO) {
        return ResponseEntity.ok(featuresService.updateFeature(featureInfoDTO));
    }

    /**
     * Delete feature.
     *
     * @param featureId id of feature to delete.
     * @return status code.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Feature not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/feature")
    public ResponseEntity<?> deleteFeature(@RequestParam("featureID") Long featureId) {
        featuresService.deleteFeature(featureId);
        return ResponseEntity.ok().build();
    }

    /**
     * Create color.
     *
     * @param colorInfoDTO id must be null.
     * @return created color.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color created.",
                    content = @Content(schema = @Schema(implementation = ColorInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/color")
    public ResponseEntity<ColorInfoDTO> createColor(@RequestBody ColorInfoDTO colorInfoDTO) {
        return ResponseEntity.ok(colorsService.createColor(colorInfoDTO));
    }

    /**
     * Update color.
     *
     * @param colorInfoDTO id must be null.
     * @return updated color.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color updated.",
                    content = @Content(schema = @Schema(implementation = ColorInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Color not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/color")
    public ResponseEntity<ColorInfoDTO> updateColor(@RequestBody ColorInfoDTO colorInfoDTO) {
        return ResponseEntity.ok(colorsService.updateColor(colorInfoDTO));
    }

    /**
     * Delete color.
     *
     * @param colorId id of color to delete.
     * @return status code.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Color not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/color")
    public ResponseEntity<?> deleteColor(@RequestParam("colorID") Long colorId) {
        colorsService.deleteColor(colorId);
        return ResponseEntity.ok().build();
    }


    /**
     * Create sport.
     *
     * @param sportInfoDTO id must be null.
     * @return created sport.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sport created.",
                    content = @Content(schema = @Schema(implementation = ColorInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/sport")
    public ResponseEntity<SportInfoDTO> createSport(@RequestBody SportInfoDTO sportInfoDTO) {
        return ResponseEntity.ok(sportsService.createSport(sportInfoDTO));
    }

    /**
     * Update sport.
     *
     * @param sportInfoDTO id must not be null.
     * @return updated sport.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sport updated.",
                    content = @Content(schema = @Schema(implementation = SportInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Sport not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @PutMapping("/sport")
    public ResponseEntity<SportInfoDTO> updateSport(@RequestBody SportInfoDTO sportInfoDTO) {
        return ResponseEntity.ok(sportsService.updateSport(sportInfoDTO));
    }

    /**
     * Delete sport.
     *
     * @param sportId id of sport to delete.
     * @return status code.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sport deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Sport not found.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))})
    @DeleteMapping("/sport")
    public ResponseEntity<?> deleteSport(@RequestParam("sportID") Long sportId) {
        sportsService.deleteSport(sportId);
        return ResponseEntity.ok().build();
    }


    /**
     * SERVICE ENDPOINT.
     * DEBUG
     *
     * @param productId product id.
     * @return product.
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") UUID productId){
        return ResponseEntity.ok(productsService.getProductById(productId));
    }

}
