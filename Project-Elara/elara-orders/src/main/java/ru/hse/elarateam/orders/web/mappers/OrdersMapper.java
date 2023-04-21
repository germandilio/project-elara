package ru.hse.elarateam.orders.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.hse.elarateam.orders.dto.response.OrderResponseDTO;
import ru.hse.elarateam.orders.model.Order;

@Mapper(uses = {OrderedItemsMapper.class, ShipmentDetailsMapper.class, PaymentDetailsMapper.class})
public interface OrdersMapper {
    @Mapping(source = "total", target = "totalWithoutDiscount")
    OrderResponseDTO orderToOrderResponseDTO(Order order);
}
