package ru.hse.elarateam.orders.web.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.orders.model.Order;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<Order, UUID>{
    @NonNull
    @Override
    Optional<Order> findById(@NonNull UUID uuid);

    @NonNull
    @Override
    Page<Order> findAll(@NonNull Pageable pageable);

    Page<Order> findAllByUserId(@NonNull UUID userId, @NonNull Pageable pageable);
}
