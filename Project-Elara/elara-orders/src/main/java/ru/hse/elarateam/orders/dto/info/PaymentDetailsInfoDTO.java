package ru.hse.elarateam.orders.dto.info;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.orders.model.PaymentStatus;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PaymentDetailsInfoDTO {
    private UUID id;
    private UUID orderId;
    private PaymentStatus status;
    private String userEmail;
    private Date updateTime;
}
