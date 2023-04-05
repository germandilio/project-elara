package ru.hse.elarateam.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.dto.request.OrderedItemRequestDTO;
import ru.hse.elarateam.model.Product;
import ru.hse.elarateam.repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository productsRepository;

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
     * the transaction is aborted, runtime exception is thrown
     * </p>
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<UUID> allocateProducts(List<OrderedItemRequestDTO> requiredProducts) {
        // id correctness check
        checkIds(requiredProducts);

        // quantity sufficiency check
        var insufficientQuantityIds = new ArrayList<UUID>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            if (productsRepository.getReferenceById(productId).getQuantity() < requiredQuantity) {
                insufficientQuantityIds.add(productId);
            }
        }
        if (!insufficientQuantityIds.isEmpty()) {
            throw new IllegalArgumentException("Insufficient quantity of these products: " + insufficientQuantityIds);
        }

        // products allocation
        var updatedProducts = new ArrayList<Product>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            var product = productsRepository.getReferenceById(productId);
            product.setQuantity(product.getQuantity() - requiredQuantity);
            updatedProducts.add(product);
        }

        // return allocated products ids
        return productsRepository.saveAllAndFlush(updatedProducts).stream().map(Product::getId).collect(Collectors.toList());
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
     * the transaction is aborted, runtime exception is thrown
     * </p>
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public List<UUID> deallocateProducts(List<OrderedItemRequestDTO> requiredProducts) {
        // id correctness check
        checkIds(requiredProducts);

        // products deallocation
        var updatedProducts = new ArrayList<Product>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            var requiredQuantity = item.getQuantity();
            var product = productsRepository.getReferenceById(productId);
            product.setQuantity(product.getQuantity() + requiredQuantity);
            updatedProducts.add(product);
        }

        // return deallocated products ids
        return productsRepository.saveAllAndFlush(updatedProducts).stream().map(Product::getId).collect(Collectors.toList());
    }

    /**
     * checks if required products exist (exists by id)
     * @param requiredProducts list of required order items (unchecked)
     */
    private void checkIds(List<OrderedItemRequestDTO> requiredProducts) {
        var incorrectIds = new ArrayList<UUID>();
        for (var item : requiredProducts) {
            var productId = item.getProductId();
            if (!productsRepository.existsById(productId)) {
                incorrectIds.add(productId);
            }
        }
        if (!incorrectIds.isEmpty()) {
            throw new IllegalArgumentException("Invalid product ids: " + incorrectIds);
        }
    }

}