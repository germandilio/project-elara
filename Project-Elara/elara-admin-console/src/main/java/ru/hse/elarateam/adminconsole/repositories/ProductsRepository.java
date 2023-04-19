package ru.hse.elarateam.adminconsole.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.adminconsole.model.Product;

import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Product, UUID> {
    @Override
    @Query("UPDATE Product product SET product.deleted = true WHERE product.id = ?1")
    @Modifying
    @Transactional
    void deleteById(UUID uuid);

    @Override
    @Query("UPDATE Product product SET product.deleted = true WHERE product = ?1")
    @Modifying
    @Transactional
    void delete(Product entity);
}

