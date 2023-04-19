package ru.hse.elarateam.products.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.products.dto.request.OrderedItemRequestDTO;
import ru.hse.elarateam.products.dto.response.ProductResponseDTO;
import ru.hse.elarateam.products.web.mappers.ProductsMapper;
import ru.hse.elarateam.products.model.Product;
import ru.hse.elarateam.products.web.repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final ProductsMapper productsMapper;

    /**
     * Checks if required products exist (exists by id)
     * Checks if required quantities are suitable (product quantity > required quantity)
     * Allocates products (product quantity -= required quantity)
     * returns list of allocated products (ids)
     *
     * @param requiredProducts list of required order items (unchecked)
     * @return allocated products ids list
     * <p>
     * If any of the above conditions is unsatisfied (e.g. invalid id or quantity)
     * the transaction is rolled back and runtime exception is thrown
     * </p>
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<ProductResponseDTO> allocateProducts(List<OrderedItemRequestDTO> requiredProducts) {
        // id correctness check
        var incorrectIds = checkIds(requiredProducts);
        if (!incorrectIds.isEmpty()) {
            log.info("Allocation failed. Incorrect ids: " + incorrectIds);
            throw new IllegalArgumentException("Allocation failed. Incorrect id found.");
        }

        // quantity sufficiency check
        var insufficientQuantityIds = new ArrayList<UUID>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            if (productsRepository.findById(productId).orElseThrow().getQuantity() < requiredQuantity) {
                insufficientQuantityIds.add(productId);
            }
        }
        if (!insufficientQuantityIds.isEmpty()) {
            log.info("Allocation failed. Insufficient quantity: " + insufficientQuantityIds);
            throw new IllegalArgumentException("Allocation failed. Insufficient quantity found.");
        }

        // products allocation
        var updatedProducts = new ArrayList<Product>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            var product = productsRepository.findById(productId).orElseThrow();
            product.setQuantity(product.getQuantity() - requiredQuantity);
            updatedProducts.add(product);
        }
        log.debug("Updated products to allocate: " + updatedProducts);

        // return allocated products response dtos
        var allocatedProducts = productsRepository.saveAllAndFlush(updatedProducts).stream()
                .map(productsMapper::productToProductResponseDTO)
                .collect(Collectors.toList());

        // add required quantities to response dtos
        for (var i = 0; i < allocatedProducts.size(); i++) {
            var requiredQuantity = requiredProducts.get(i).getQuantity();
            allocatedProducts.get(i).setQuantity(requiredQuantity);
        }

        log.info("Products allocated: " + allocatedProducts);

        return allocatedProducts;
    }

    /**
     * Checks if required products exist (exists by id)
     * Deallocates products (product quantity += required quantity)
     * returns list of deallocated products (ids)
     *
     * @param requiredProducts list of required order items (unchecked)
     * @return deallocated products ids list
     *
     * <p>
     * If any of the above conditions is unsatisfied (e.g. invalid id)
     * the transaction is rolled back and runtime exception is thrown
     * </p>
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<UUID> deallocateProducts(List<OrderedItemRequestDTO> requiredProducts) {
        // id correctness check
        var incorrectIds = checkIds(requiredProducts);
        if (!incorrectIds.isEmpty()) {
            log.info("Deallocation failed. Incorrect ids: " + incorrectIds);
            throw new IllegalArgumentException("Deallocation failed. Incorrect id found.");
        }

        // products deallocation
        var updatedProducts = new ArrayList<Product>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            var product = productsRepository.findById(productId).orElseThrow();
            product.setQuantity(product.getQuantity() + requiredQuantity);
            updatedProducts.add(product);
        }
        log.debug("Updated products to deallocate: " + updatedProducts);

        // return deallocated products ids
        var deallocatedProducts = productsRepository.saveAllAndFlush(updatedProducts).stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        log.info("Products deallocated: " + deallocatedProducts);

        return deallocatedProducts;
    }

    /**
     * checks if required products exist (exists by id)
     *
     * @param requiredProducts list of required order items (unchecked)
     */
    private List<UUID> checkIds(List<OrderedItemRequestDTO> requiredProducts) {
        var incorrectIds = new ArrayList<UUID>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            if (!productsRepository.existsById(productId)) {
                incorrectIds.add(productId);
            }
        }
        return incorrectIds;
    }
}
