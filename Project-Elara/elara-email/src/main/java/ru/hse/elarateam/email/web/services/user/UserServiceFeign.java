package ru.hse.elarateam.email.web.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.email.dto.UserDTO;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserServiceFeign implements UserService {

    private final UserServiceFeignClient userServiceFeignClient;

    @Override
    public UserDTO getUserById(UUID userId) {
        var userDTO = userServiceFeignClient.getUserById(userId);
        if (!userDTO.getUserId().equals(userId)) {
            log.error("User ID mismatch, got {} from users service, expected {}", userDTO.getUserId(), userId);
        }
        log.debug("User is {}", userDTO);
        return userDTO;
    }
}
