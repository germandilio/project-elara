package ru.hse.elarateam.payment.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hse.elarateam.payment.model.PaymentDetails;

import java.util.UUID;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
}
