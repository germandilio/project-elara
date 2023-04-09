package ru.hse.elarateam.email.web.services.user;

import ru.hse.elarateam.email.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUserById(UUID userId);
}
