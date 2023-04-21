package ru.hse.elarateam.messages;

import lombok.Builder;
import lombok.Data;
import ru.hse.elarateam.users.dto.OrderCheckoutDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class OrderCheckoutMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = -2240538216079077820L;

    private UUID userId;

    private OrderCheckoutDTO orderCheckoutDTO;
}
