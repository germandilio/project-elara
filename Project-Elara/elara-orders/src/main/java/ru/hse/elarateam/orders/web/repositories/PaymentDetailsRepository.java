package ru.hse.elarateam.orders.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.orders.model.PaymentDetails;

import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
}
