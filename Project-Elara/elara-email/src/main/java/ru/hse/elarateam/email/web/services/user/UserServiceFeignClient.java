package ru.hse.elarateam.email.web.services.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hse.elarateam.email.configs.auth.UserServiceConfig;
import ru.hse.elarateam.email.dto.UserDTO;

import java.util.UUID;

@FeignClient(name = "elara-users", configuration = UserServiceConfig.class, url = "http://localhost:8085")
public interface UserServiceFeignClient {
    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/users/{userId}", produces = "application/json")
    @ResponseBody
    UserDTO getUserById(@PathVariable UUID userId);
}
