package ru.hse.elarateam.orders.web.mappers;

import org.mapstruct.Mapper;
import ru.hse.elarateam.orders.dto.request.OrderedItemRequestDTO;
import ru.hse.elarateam.orders.dto.response.OrderedItemResponseDTO;
import ru.hse.elarateam.orders.model.OrderedItem;

import java.util.Set;

@Mapper
public interface OrderedItemsMapper {
    OrderedItemRequestDTO orderedItemToOrderedItemRequestDTO(OrderedItem orderedItem);

    OrderedItemResponseDTO orderedItemToOrderedItemResponseDTO(OrderedItem orderedItem);

    Set<OrderedItemResponseDTO> map(Set<OrderedItem> items);
}
