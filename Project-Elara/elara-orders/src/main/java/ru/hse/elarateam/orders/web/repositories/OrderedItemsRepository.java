package ru.hse.elarateam.orders.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.orders.model.OrderedItem;

import java.util.UUID;

@Repository
public interface OrderedItemsRepository extends JpaRepository<OrderedItem, UUID> {
}
