package ru.hse.elarateam.payment.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.payment.dto.info.PaymentDetailsInfoDTO;
import ru.hse.elarateam.payment.model.PaymentDetails;

@Mapper
public interface PaymentDetailsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "userEmail", target = "userEmail")
    PaymentDetails paymentDetailsInfoDTOtoPaymentDetails(PaymentDetailsInfoDTO paymentDetailsInfoDTO);

    PaymentDetailsInfoDTO paymentDetailsToPaymentDetailsInfoDTO(PaymentDetails paymentDetails);
}
