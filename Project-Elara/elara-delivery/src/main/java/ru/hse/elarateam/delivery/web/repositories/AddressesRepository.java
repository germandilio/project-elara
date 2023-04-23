package ru.hse.elarateam.delivery.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.delivery.model.Address;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressesRepository extends JpaRepository<Address, UUID> {
    List<Address> findByUserId(UUID userId);
}
