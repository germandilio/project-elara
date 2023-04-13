package ru.hse.elarateam.adminconsole.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.dto.FeatureInfoDTO;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.dto.SportInfoDTO;

import java.util.UUID;

@RestController
@RequestMapping("/v1/admin-console")
public class AdminConsoleController {
    // todo доделать exceptions

    /**
     * Create product by productInfoDTO.
     *
     * @param productInfoDTO product info.
     * @param token          JWT token.
     * @return productInfoDTO if product was created, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created.",
                    content = @Content(schema = @Schema(implementation = ProductInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/product")
    public ResponseEntity<ProductInfoDTO> createProduct(@RequestHeader("Authorization") String token,
                                                        @RequestBody ProductInfoDTO productInfoDTO) {
        return null;
    }

    /**
     * Update product by productInfoDTO.
     *
     * @param productInfoDTO product info.
     * @param token          JWT token.
     * @return productInfoDTO if product was updated, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated.",
                    content = @Content(schema = @Schema(implementation = ProductInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/product")
    public ResponseEntity<ProductInfoDTO> updateProduct(@RequestHeader("Authorization") String token,
                                                        @RequestBody ProductInfoDTO productInfoDTO) {
        return null;
    }

    /**
     * Delete product by id.
     *
     * @param productId product id.
     * @param token     JWT token.
     * @return productInfoDTO if product was deleted, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestHeader("Authorization") String token,
                                           @RequestParam("productID") UUID productId) {
        return null;
    }

    /**
     * Create feature by featureInfoDTO.
     *
     * @param featureInfoDTO feature info.
     * @param token          JWT token.
     * @return featureInfoDTO if feature was created, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature created.",
                    content = @Content(schema = @Schema(implementation = FeatureInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> createFeature(@RequestHeader("Authorization") String token,
                                                        @RequestBody FeatureInfoDTO featureInfoDTO) {
        return null;
    }

    /**
     * Update feature by featureInfoDTO.
     *
     * @param featureInfoDTO feature info.
     * @param token          JWT token.
     * @return featureInfoDTO if feature was updated, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature updated.",
                    content = @Content(schema = @Schema(implementation = FeatureInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> updateFeature(@RequestHeader("Authorization") String token,
                                                        @RequestBody FeatureInfoDTO featureInfoDTO) {
        return null;
    }

    /**
     * Delete feature by id.
     *
     * @param featureId feature id.
     * @param token     JWT token.
     * @return featureInfoDTO if feature was deleted, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Feature deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/feature")
    public ResponseEntity<?> deleteFeature(@RequestHeader("Authorization") String token,
                                           @RequestParam("featureID") Long featureId) {
        return null;
    }

    /**
     * Create color by colorInfoDTO.
     *
     * @param colorInfoDTO color info.
     * @param token        JWT token.
     * @return colorInfoDTO if color was created, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color created.",
                    content = @Content(schema = @Schema(implementation = ColorInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/color")
    public ResponseEntity<ColorInfoDTO> createColor(@RequestHeader("Authorization") String token,
                                                    @RequestBody ColorInfoDTO colorInfoDTO) {
        return null;
    }

    /**
     * Update color by colorInfoDTO.
     *
     * @param colorInfoDTO color info.
     * @param token        JWT token.
     * @return colorInfoDTO if color was updated, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color created.",
                    content = @Content(schema = @Schema(implementation = FeatureInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/color")
    public ResponseEntity<ColorInfoDTO> updateColor(@RequestHeader("Authorization") String token,
                                                    @RequestBody ColorInfoDTO colorInfoDTO) {
        return null;
    }

    /**
     * Delete color by id.
     *
     * @param colorId color id.
     * @param token   JWT token.
     * @return colorInfoDTO if color was deleted, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Color deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/color")
    public ResponseEntity<?> deleteColor(@RequestHeader("Authorization") String token,
                                         @RequestParam("colorID") Long colorId) {
        return null;
    }

    /**
     * Create sport by sportInfoDTO.
     *
     * @param sportInfoDTO sport info.
     * @param token        JWT token.
     * @return sportInfoDTO if sport was created, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sport created.",
                    content = @Content(schema = @Schema(implementation = SportInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/sport")
    public ResponseEntity<SportInfoDTO> createSport(@RequestHeader("Authorization") String token,
                                                    @RequestBody SportInfoDTO sportInfoDTO) {
        return null;
    }

    /**
     * Update sport by sportInfoDTO.
     *
     * @param sportInfoDTO sport info.
     * @param token        JWT token.
     * @return sportInfoDTO if sport was updated, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sport updated.",
                    content = @Content(schema = @Schema(implementation = SportInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/sport")
    public ResponseEntity<SportInfoDTO> updateSport(@RequestHeader("Authorization") String token,
                                                    @RequestBody SportInfoDTO sportInfoDTO) {
        return null;
    }

    /**
     * Delete sport by id.
     *
     * @param sportId sport id.
     * @param token   JWT token.
     * @return sportInfoDTO if sport was deleted, string exception message otherwise.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sport deleted."),
            @ApiResponse(responseCode = "401", description = "Unauthorized.",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error.",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/sport")
    public ResponseEntity<?> deleteSport(@RequestHeader("Authorization") String token,
                                         @RequestParam("sportID") Long sportId) {
        return null;
    }

}
