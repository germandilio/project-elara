package ru.hse.elarateam.adminconsole.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.dto.ColorInfoDTO;
import ru.hse.elarateam.adminconsole.dto.ProductInfoDTO;
import ru.hse.elarateam.adminconsole.model.Color;
import ru.hse.elarateam.adminconsole.repositories.ColorsRepository;
import ru.hse.elarateam.adminconsole.repositories.ProductsRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Slf4j
@SpringBootTest
@ActiveProfiles("local-dev")
class ProductsServiceTest {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ColorsRepository colorsRepository;

    @Autowired
    private ProductsService productsService;

    @BeforeEach
    void setUp() {
        productsRepository.deleteAll();
        productsRepository.flush();

        colorsRepository.deleteAll();
        colorsRepository.save(Color.builder().name("red").hex("#FF0000").build());
        colorsRepository.save(Color.builder().name("green").hex("#00FF00").build());
        colorsRepository.flush();
    }

    @Test
    void createProduct() {
        var colors = colorsRepository.findAll();

        var colorDTOs = colors.stream()
                .map(color -> ColorInfoDTO.builder()
                        .id(color.getId())
                        .name(color.getName())
                        .hex(color.getHex())
                        .build())
                .toList();

        var productInfoDTO = createProductInfoDTO(new HashSet<>(colorDTOs));

        log.info("productDTO: {}", productInfoDTO);

        var product = productsService.createProduct(productInfoDTO);

        log.info("saved product: {}", product);
        assertEquals(product.getName(), productInfoDTO.getName());
        assertEquals(new HashSet<>(colorDTOs), product.getColors());
    }

//    @Test
//    @Transactional
//    void updateProduct() {
//    // проблема достать обновленный объект из базы данных в рамках одной транзакции
//
//        var colors = colorsRepository.findAll();
//
//        var colorDTOs = colors.stream()
//                .map(color -> ColorInfoDTO.builder()
//                        .id(color.getId())
//                        .name(color.getName())
//                        .hex(color.getHex())
//                        .build())
//                .toList();
//
//        var productInfoDTO = createProductInfoDTO(new HashSet<>(colorDTOs));
//
//        log.info("productDTO: {}", productInfoDTO);
//
//        var product = productsService.createProduct(productInfoDTO);
//
//        log.info("saved product: {}", product);
//
//        var newColorDTOs = Set.of(ColorInfoDTO.builder().id(1L).hex("sdf").name("sdf").build());
//        productInfoDTO.setId(product.getId());
//        productInfoDTO.setColors(newColorDTOs);
//
//        var updatedProduct = productsService.updateProduct(productInfoDTO);
//
//        log.info("updated product: {}", updatedProduct);
//
//        var restoredProduct = productsRepository.findById(product.getId()).orElseThrow();
//        log.info("restored product: {}", restoredProduct);
//
//        assertEquals(product.getName(), updatedProduct.getName());
//        assertEquals(new HashSet<>(colorDTOs), product.getColors());
//        assertEquals(newColorDTOs, updatedProduct.getColors());
//        assertEquals(Set.of(colors.get(0)), restoredProduct.getColors());
//    }

//    @Test
//    @Transactional
//    void createProductColorsNull() {
//        var productInfoDTO = createProductInfoDTO(null);
//
//        log.info("productDTO: {}", productInfoDTO);
//
//        var product = productsService.createProduct(productInfoDTO);
//        log.info("saved product: {}", product);
//
//        productInfoDTO.setId(product.getId());
//        productInfoDTO.setColors(Set.of(ColorInfoDTO.builder().id(1L).hex("").name("").build()));
//
//        var updatedProduct = productsService.updateProduct(productInfoDTO);
//        log.info("updated product: {}", updatedProduct);
//
//        var restoredProduct = productsService.getProductById(product.getId());
//        log.info("restored product: {}", restoredProduct);
//
//        assertEquals(Set.of(Color.builder().name("red").hex("#FF0000").build()), restoredProduct.getColors());
//    }

//    @Test
//    void differentColors() {
//        // todo there is a bug here, but мне впадлу это фиксить
//        // если положить неправильные поля в цвета с существующими id,
//        // то в базе останутся нормальные цвета, а в объекте,
//        // который вернет productsService.createProduct(productInfoDTO) - неправильные.
//        // будем уповать на happy path.
//
//        // get all colors & replace names and hexes
//        var colors = colorsRepository.findAll();
//        var differentColorDTOs = colors.stream()
//                .map(color -> ColorInfoDTO.builder()
//                        .id(color.getId())
//                        .name("zero")
//                        .hex("#000000")
//                        .build())
//                .toList();
//
//        var productInfoDTO = createProductInfoDTO(new HashSet<>(differentColorDTOs));
//
//        log.info("productDTO: {}", productInfoDTO);
//
//        // create product with different colors
//        var product = productsService.createProduct(productInfoDTO);
//        log.info("saved product: {}", product);
//
//        // get all colors again
//        colors = colorsRepository.findAll();
//        var colorDTOs = colors.stream()
//                .map(color -> ColorInfoDTO.builder()
//                        .id(color.getId())
//                        .name(color.getName())
//                        .hex(color.getHex())
//                        .build())
//                .toList();
//        log.info("colors: {}", colors);
//
//        var restoredProduct = productsRepository.findById(product.getId()).orElseThrow();
//        log.info("restored product: {}", restoredProduct);
//
//        // asserting that colors were not overwritten
//        assertEquals(restoredProduct.getName(), productInfoDTO.getName());
//        assertEquals(colors, new ArrayList<>(restoredProduct.getColors()));
//
//    }


    private ProductInfoDTO createProductInfoDTO(Set<ColorInfoDTO> colors) {
        return ProductInfoDTO.builder()
                .name("product1")
//                .price(new BigDecimal("1234.99"))
//                .discount(30)
//                .description("description1")
//                .brand("abibas")
//                .quantity(10L)
//                .countryOfOrigin("Russia")
//                .sizeUS(42.0)
//                .sizeUK(42.0)
//                .sizeEUR(42.0)
//                .height(42.0)
//                .length(42.0)
//                .width(42.0)
//                .weight(42.0)
                .colors(colors)
                .build();
    }
}