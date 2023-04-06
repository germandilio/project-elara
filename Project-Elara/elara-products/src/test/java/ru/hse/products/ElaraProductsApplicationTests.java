package ru.hse.products;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.hse.products.dto.request.OrderedItemRequestDTO;
import ru.hse.products.model.Product;
import ru.hse.products.repositories.ProductsRepository;
import ru.hse.products.services.ProductsService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("local-dev")
public class ElaraProductsApplicationTests {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @BeforeEach
    public void setUp() {
        productsRepository.deleteAll();
        productsRepository.save(Product.builder()
                .upc("43cfa7bd-a382-4e6d-8eb2-e0fdfea37cb5")
                .name("product1")
                .price(new BigDecimal("1234.99"))
                .discount(30)
                .description("description1")
                .brand("abibas")
                .quantity(10L)
                .countryOfOrigin("Russia")
                .sizeUS(42)
                .sizeUK(42)
                .sizeEUR(42)
                .height(42)
                .length(42)
                .width(42)
                .weight(42)
                .build());
        productsRepository.save(Product.builder()
                .upc("43cfa7bd-a382-4e6d-8eb2-e0fdfea37adf")
                .name("product2")
                .price(new BigDecimal("12354.99"))
                .discount(30)
                .description("description2")
                .brand("noik")
                .quantity(10L)
                .countryOfOrigin("Russia")
                .sizeUS(42)
                .sizeUK(42)
                .sizeEUR(42)
                .height(42)
                .length(42)
                .width(42)
                .weight(42)
                .build());
        productsRepository.flush();
    }

    @Test
    public void contextLoads() {
        assertEquals(2, productsRepository.findAll().size());
    }

    @Test
    public void testDeleted() {
        var products = productsRepository.findAll();

        Assertions.assertEquals(false, products.get(0).getDeleted());
        Assertions.assertEquals(false, products.get(1).getDeleted());
    }

    @Test
    public void testAllocateProductsInvalidIds() {
        var uuid1 = UUID.randomUUID();
        var uuid2 = UUID.randomUUID();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.allocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build())));

        assertEquals(2, productsRepository.findAll().size());
        assertEquals("Allocation failed. Incorrect id found.", thrown.getMessage());
    }

    @Test
    public void testAllocateProductsInsufficientQuantity() {
        var products = productsRepository.findAll();
        var uuid1 = products.get(0).getId();
        var uuid2 = products.get(1).getId();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.allocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(100L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(100L).build())));

        assertEquals(2, productsRepository.findAll().size());
        assertEquals("Allocation failed. Insufficient quantity found.", thrown.getMessage());
    }

    @Test
    public void testAllocateProducts() {
        var products = productsRepository.findAll();
        var uuid1 = products.get(0).getId();
        var uuid2 = products.get(1).getId();

        var allocatedProducts = productsService.allocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build()));

        assertEquals(List.of(uuid1, uuid2), allocatedProducts);
        assertEquals(2, productsRepository.findAll().size());
        Assertions.assertEquals(5L, productsRepository.findById(uuid1).orElseThrow().getQuantity());
        Assertions.assertEquals(5L, productsRepository.findById(uuid2).orElseThrow().getQuantity());
    }

    @Test
    public void testDeallocateProductsInvalidIds() {
        var uuid1 = UUID.randomUUID();
        var uuid2 = UUID.randomUUID();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.deallocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build())));

        assertEquals(2, productsRepository.findAll().size());
        assertEquals("Deallocation failed. Incorrect id found.", thrown.getMessage());
    }
    @Test
    public void testDeallocateProducts() {
        var products = productsRepository.findAll();
        var uuid1 = products.get(0).getId();
        var uuid2 = products.get(1).getId();

        var deallocatedProducts = productsService.deallocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build()));

        assertEquals(List.of(uuid1, uuid2), deallocatedProducts);
        assertEquals(2, productsRepository.findAll().size());
        Assertions.assertEquals(15L, productsRepository.findById(uuid1).orElseThrow().getQuantity());
        Assertions.assertEquals(15L, productsRepository.findById(uuid2).orElseThrow().getQuantity());
    }
}
