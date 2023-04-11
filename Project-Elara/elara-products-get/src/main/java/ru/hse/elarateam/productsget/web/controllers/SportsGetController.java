package ru.hse.elarateam.productsget.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.elarateam.productsget.dto.SportInfoDTO;
import ru.hse.elarateam.productsget.web.service.ProductService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/sports")
@RestController
public class SportsGetController {

    private final ProductService productsService;

    /**
     * Get sport by id
     *
     * @param id sport id
     * @return sport info
     */
    @GetMapping("/{id}")
    public ResponseEntity<SportInfoDTO> getSportById(@PathVariable Long id) {
        return ResponseEntity.ok(productsService.getSport(id));
    }

    /**
     * Get all sports
     *
     * @return list of sports
     */
    @GetMapping
    public ResponseEntity<List<SportInfoDTO>> getSports() {
        return ResponseEntity.ok(productsService.getAllSports());
    }
}
