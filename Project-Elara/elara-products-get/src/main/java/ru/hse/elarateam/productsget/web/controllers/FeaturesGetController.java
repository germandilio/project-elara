package ru.hse.elarateam.productsget.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.elarateam.productsget.dto.FeatureInfoDTO;
import ru.hse.elarateam.productsget.web.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/features")
@RestController
public class FeaturesGetController {

    private final ProductService productsService;

    /**
     * Get feature by id
     *
     * @param id feature id
     * @return feature info
     */
    @GetMapping("/{id}")
    public ResponseEntity<FeatureInfoDTO> getFeatureById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getFeature(id));
    }

    /**
     * Get all features
     *
     * @return list of features
     */
    @GetMapping
    public ResponseEntity<List<FeatureInfoDTO>> getFeatures() {
        return ResponseEntity.ok(productsService.getAllFeatures());
    }

}
