package ru.hse.elarateam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.hse.elarateam.dto.request.OrderedItemRequestDTO;
import ru.hse.elarateam.model.Product;
import ru.hse.elarateam.repositories.ProductsRepository;
import ru.hse.elarateam.services.ProductsService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ElaraProductsApplicationTests {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsService productsService;

    @Test
    public void contextLoads() {
    }

    @BeforeEach
    public void setUp() {
        productsRepository.save(Product.builder().name("product1").quantity(10L).build());
        productsRepository.save(Product.builder().name("product12").quantity(10L).build());
        productsRepository.flush();
    }

    @Test
    public void testAllocateProductsInvalidIds() {
        var uuid1 = UUID.randomUUID();
        var uuid2 = UUID.randomUUID();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.allocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build())));

        assertEquals("Invalid product ids: " + List.of(uuid1, uuid2), thrown.getMessage());
    }

    @Test
    public void testAllocateProductsInsufficientQuantity() {
        var products = productsRepository.findAll();
        var uuid1 = products.get(0).getId();
        var uuid2 = products.get(1).getId();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.allocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(100L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(100L).build())));

        assertEquals("Insufficient quantity of these products: " + List.of(uuid1, uuid2), thrown.getMessage());
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
        //todo fix LazyInitializationException
//        assertEquals(5L, productsRepository.getReferenceById(uuid1).getQuantity());
//        assertEquals(5L, productsRepository.getReferenceById(uuid2).getQuantity());
    }

    @Test
    public void testDeallocateProductsInvalidIds() {
        var uuid1 = UUID.randomUUID();
        var uuid2 = UUID.randomUUID();

        var thrown = assertThrows(IllegalArgumentException.class, () -> productsService.deallocateProducts(List.of(
                OrderedItemRequestDTO.builder().productId(uuid1).quantity(5L).build(),
                OrderedItemRequestDTO.builder().productId(uuid2).quantity(5L).build())));

        assertEquals("Invalid product ids: " + List.of(uuid1, uuid2), thrown.getMessage());
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
        //todo fix LazyInitializationException
//        assertEquals(15L, productsRepository.getReferenceById(uuid2).getQuantity());
//        assertEquals(15L, productsRepository.getReferenceById(uuid2).getQuantity());
    }
}
