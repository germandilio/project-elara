package ru.hse.elarateam.users.dto.messages;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.users.dto.OrderCheckoutDTO;

import java.util.UUID;

@Data
@Builder
public class OrderCheckoutMessage {
    private UUID userId;

    private OrderCheckoutDTO orderCheckoutDTO;
}
