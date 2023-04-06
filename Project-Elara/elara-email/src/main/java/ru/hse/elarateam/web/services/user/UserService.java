package ru.hse.elarateam.web.services.user;

import ru.hse.elarateam.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID userId);
}
