package ru.hse.elarateam.delivery.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.delivery.model.ShipmentDetails;

import java.util.UUID;

@Repository
public interface ShipmentDetailsRepository extends JpaRepository<ShipmentDetails, UUID> {
}
