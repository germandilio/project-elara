package ru.hse.elarateam.payment.dto.info;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.payment.model.status.PaymentStatus;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PaymentDetailsInfoDTO {
    private UUID orderId;
    private PaymentStatus status;
    private String userEmail;
    private Date updateTime;
}
