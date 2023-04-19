package ru.hse.elarateam.delivery.web.repositories;

import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.delivery.model.ShipmentMethod;

@Repository
public interface ShipmentMethodsRepository extends JpaRepository<ShipmentMethod, UUID> {
}
