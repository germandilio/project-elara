package ru.hse.elarateam.orders.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserServiceInfoDTO {
    private UUID userId;
    private String login;
    private UUID roleId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RoleEnum roleName;
}
