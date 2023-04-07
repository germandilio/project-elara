package ru.hse.elarateam.products.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePayloadDTO {
    private String message;
    private Object data;
}
