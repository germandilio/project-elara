package ru.hse.elarateam.productsget.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.elarateam.productsget.dto.ColorInfoDTO;
import ru.hse.elarateam.productsget.web.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/colors")
@RestController
public class ColorsGetController {

    private final ProductService productsService;

    /**
     * Get color by id
     *
     * @param id color id
     * @return color info
     */
    @GetMapping("/{id}")
    public ResponseEntity<ColorInfoDTO> getColorById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getColor(id));
    }

    /**
     * Get all colors
     *
     * @return list of colors
     */
    @GetMapping
    public ResponseEntity<List<ColorInfoDTO>> getColors() {
        return ResponseEntity.ok(productsService.getAllColors());
    }
}
