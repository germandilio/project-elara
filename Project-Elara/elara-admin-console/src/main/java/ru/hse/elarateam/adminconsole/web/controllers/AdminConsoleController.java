package ru.hse.elarateam.adminconsole.web.controllers;

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
    @PostMapping("/product")
    public ResponseEntity<ProductInfoDTO> createProduct(@RequestBody ProductInfoDTO productInfoDTO) {
        return null;
    }

    @PutMapping("/product")
    public ResponseEntity<ProductInfoDTO> updateProduct(@RequestBody ProductInfoDTO productInfoDTO) {
        return null;
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestParam("productID") UUID productId) {
        return null;
    }

    @PostMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> createFeature(@RequestBody FeatureInfoDTO featureInfoDTO) {
        return null;
    }

    @PutMapping("/feature")
    public ResponseEntity<FeatureInfoDTO> updateFeature(@RequestBody FeatureInfoDTO featureInfoDTO) {
        return null;
    }

    @DeleteMapping("/feature")
    public ResponseEntity<?> deleteFeature(@RequestParam("featureID") Long featureId) {
        return null;
    }

    @PostMapping("/color")
    public ResponseEntity<ColorInfoDTO> createColor(@RequestBody ColorInfoDTO colorInfoDTO) {
        return null;
    }

    @PutMapping("/color")
    public ResponseEntity<ColorInfoDTO> updateColor(@RequestBody ColorInfoDTO colorInfoDTO) {
        return null;
    }

    @DeleteMapping("/color")
    public ResponseEntity<?> deleteColor(@RequestParam("colorID") Long colorId) {
        return null;
    }

    @PostMapping("/sport")
    public ResponseEntity<SportInfoDTO> createSport(@RequestBody SportInfoDTO sportInfoDTO) {
        return null;
    }

    @PutMapping("/sport")
    public ResponseEntity<SportInfoDTO> updateSport(@RequestBody SportInfoDTO sportInfoDTO) {
        return null;
    }

    @DeleteMapping("/sport")
    public ResponseEntity<?> deleteSport(@RequestParam("sportID") Long sportId) {
        return null;
    }

}
