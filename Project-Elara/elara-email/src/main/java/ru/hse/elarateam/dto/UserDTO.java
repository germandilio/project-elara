package ru.hse.elarateam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID userId;

    private String email;

    private String firstName;
}
