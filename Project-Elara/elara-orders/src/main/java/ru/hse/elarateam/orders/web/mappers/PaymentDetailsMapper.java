package ru.hse.elarateam.orders.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.orders.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.orders.model.PaymentDetails;

@Mapper
public interface PaymentDetailsMapper {
    PaymentDetails paymentDetailsInfoDTOToPaymentDetails(PaymentDetailsInfoDTO paymentDetailsInfoDTO);

    PaymentDetailsInfoDTO paymentDetailsToPaymentDetailsInfoDTO(PaymentDetails paymentDetails);
}
