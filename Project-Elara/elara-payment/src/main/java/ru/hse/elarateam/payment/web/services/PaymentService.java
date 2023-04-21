package ru.hse.elarateam.payment.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.payment.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.payment.model.Order;
import ru.hse.elarateam.payment.model.status.OrderStatus;
import ru.hse.elarateam.payment.web.mappers.PaymentDetailsMapper;
import ru.hse.elarateam.payment.web.repositories.OrdersRepository;
import ru.hse.elarateam.payment.web.repositories.PaymentDetailsRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final PaymentDetailsMapper paymentDetailsMapper;
    private final OrdersRepository ordersRepository;

    @Transactional(rollbackFor = RuntimeException.class)
    public void registerPayment(PaymentDetailsInfoDTO paymentDetailsInfoDTO) {
        var paymentDetails = paymentDetailsMapper.paymentDetailsInfoDTOtoPaymentDetails(paymentDetailsInfoDTO);
        log.debug("got payment details: " + paymentDetails);
        var orderId = paymentDetails.getOrderId();

        var order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id" + orderId + " not found."));

        checkOrderStatus(order);

        paymentDetails = paymentDetailsRepository.saveAndFlush(paymentDetails);
        log.info("Payment details saved: " + paymentDetails);
        order.setPaymentDetails(paymentDetails);
        order.setStatus(OrderStatus.PAID);
        order = ordersRepository.saveAndFlush(order);
        log.info("Payment details assigned to order: " + order);
    }

    private void checkOrderStatus(Order order) {
        if (order.getStatus() == OrderStatus.PAID) {
            log.error("Order " + order.getId() + " is already paid.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already paid.");
        }
        if (order.getStatus() == OrderStatus.PACKED) {
            log.error("Order " + order.getId() + " is already packed.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already packed.");
        }
        if (order.getStatus() == OrderStatus.IN_DELIVERY) {
            log.error("Order " + order.getId() + " is already in delivery.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already in delivery.");
        }
        if (order.getStatus() == OrderStatus.DELIVERED) {
            log.error("Order " + order.getId() + " is already delivered.");
            throw new IllegalArgumentException("Order " + order.getId() + " is already delivered.");
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            log.error("Order " + order.getId() + " is cancelled.");
            throw new IllegalArgumentException("Order " + order.getId() + " is cancelled.");
        }

    }
}
