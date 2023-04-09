package ru.hse.elarateam.users.web.services.auth.loginservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.hse.elarateam.users.configs.auth.LoginServiceConfig;
import ru.hse.elarateam.users.web.services.auth.dto.UserServiceInfoDTO;

/**
 * Feign client for calling login (auth) service.
 */
@FeignClient(name = "elara-login", url = "http://localhost:8086", configuration = LoginServiceConfig.class)
public interface LoginServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/auth/user", produces = "application/json")
    @ResponseBody
    UserServiceInfoDTO getUserInfoByToken(@RequestParam String token);
}
