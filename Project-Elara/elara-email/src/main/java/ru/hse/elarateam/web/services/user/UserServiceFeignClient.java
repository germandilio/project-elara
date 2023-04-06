package ru.hse.elarateam.web.services.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hse.elarateam.dto.UserDTO;
import ru.hse.elarateam.web.services.user.config.UserServiceConfig;

import java.util.UUID;

@FeignClient(name = "elara-users", configuration = UserServiceConfig.class)
public interface UserServiceFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "users/{userId}/email", produces = "application/json")
    @ResponseBody
    UserDTO getUserEmailById(@PathVariable UUID userId);
}
