package ru.hse.elarateam.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.orders.model.ShipmentDetails;

import java.util.UUID;

@Repository
public interface ShipmentDetailsRepository extends JpaRepository<ShipmentDetails, UUID> {
}
