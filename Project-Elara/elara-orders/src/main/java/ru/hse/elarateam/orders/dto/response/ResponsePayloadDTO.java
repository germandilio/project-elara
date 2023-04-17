package ru.hse.elarateam.orders.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponsePayloadDTO<T> {
    private String message;
    private T data;
}
