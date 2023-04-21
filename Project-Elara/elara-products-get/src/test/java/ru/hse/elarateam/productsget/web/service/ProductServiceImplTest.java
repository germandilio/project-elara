package ru.hse.elarateam.productsget.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import ru.hse.elarateam.productsget.dto.ProductInfoDTO;
import ru.hse.elarateam.productsget.web.mappers.SportMapper;
import ru.hse.elarateam.productsget.web.repository.ColorsRepository;
import ru.hse.elarateam.productsget.web.repository.FeatureRepository;
import ru.hse.elarateam.productsget.web.repository.ProductsRepository;
import ru.hse.elarateam.productsget.web.repository.SportsRepository;
import ru.hse.elarateam.productsget.web.mappers.ColorMapper;
import ru.hse.elarateam.productsget.web.mappers.FeatureMapper;
import ru.hse.elarateam.productsget.web.mappers.ProductsMapper;

@SpringBootTest
@ActiveProfiles("local-dev")
@ExtendWith(MockitoExtension.class)
@DisplayName("ProductServiceImpl tests")
class ProductServiceImplTest {
    private ProductServiceImpl productService;

    @Mock
    private ProductsRepository productRepository;
    @Mock
    private FeatureRepository featureRepository;
    @Mock
    private ColorsRepository colorsRepository;
    @Mock
    private SportsRepository sportsRepository;

    @Autowired
    private ProductsMapper productsMapper;
    @Autowired
    private ColorMapper colorMapper;
    @Autowired
    private FeatureMapper featureMapper;
    @Autowired
    private SportMapper sportMapper;


    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository, featureRepository, colorsRepository, sportsRepository,
                productsMapper, featureMapper, colorMapper, sportMapper);
    }

    @Nested
    @DisplayName("Tests for getProductInfo method")
    class GetProductInfoTests {
        @Test
        @DisplayName("Test that getProductInfo returns the expected ProductInfoDTO")
        void getProductInfoTest() {
            // Arrange
            UUID productId = UUID.randomUUID();
            ProductInfoDTO expectedProductInfoDTO = ProductInfoDTO.builder()
                    .id(productId)
                    .build();

            // Mock
            //when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProductInfoDTO));

            // Act
            ProductInfoDTO actualProductInfoDTO = productService.getProductInfo(productId);

            // Assert
            assertNotNull(actualProductInfoDTO);
            assertEquals(expectedProductInfoDTO.getId(), actualProductInfoDTO.getId());
        }
    }
}
