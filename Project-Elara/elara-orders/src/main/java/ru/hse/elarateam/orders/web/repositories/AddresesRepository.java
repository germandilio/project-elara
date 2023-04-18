package ru.hse.elarateam.orders.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.orders.model.Address;

import java.util.UUID;

@Repository
public interface AddresesRepository extends JpaRepository<Address, UUID> {
}
