package ru.hse.elarateam.email.web.services.user;

import feign.FeignException;
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
        try {
            var userDTO = userServiceFeignClient.getUserById(userId);
            if (!userDTO.getUserId().equals(userId)) {
                log.error("User ID mismatch, got {} from users service, expected {}", userDTO.getUserId(), userId);
            }
            log.debug("User is {}", userDTO);
            return userDTO;

        } catch (FeignException.NotFound ex) {
            log.error("User with ID {} not found", userId);
            throw new IllegalArgumentException("User with ID " + userId + " not found", ex);
        } catch (FeignException.Unauthorized ex) {
            log.error("Authorization in users service failed");
            throw new IllegalStateException("Authorization in users service failed", ex);
        } catch (FeignException.BadRequest ex) {
            log.error("Bad request to users service ", ex);
        } catch (FeignException.InternalServerError ex) {
            log.error("Users service is unavailable");
            throw new IllegalStateException("Users service is unavailable", ex);
        } catch (Exception ex) {
            log.error("Error while getting user by ID", ex);
            throw new IllegalStateException("Error while getting user by ID", ex);
        }

        return null;
    }
}
