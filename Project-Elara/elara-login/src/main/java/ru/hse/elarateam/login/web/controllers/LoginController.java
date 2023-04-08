package ru.hse.elarateam.login.web.controllers;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.login.dto.JwtResponse;
import ru.hse.elarateam.login.dto.UserServiceInfoDTO;
import ru.hse.elarateam.login.web.services.jwt.JWTUtils;
import ru.hse.elarateam.login.web.services.jwt.service.ServiceTokenUtils;
import ru.hse.elarateam.login.web.services.users.UsersService;

@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@RestController
public class LoginController {

    private final JWTUtils jwtUtils;

    private final UsersService usersService;

    private final ServiceTokenUtils serviceTokenUtils;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam @NotNull @Email String login) {
        final var user = usersService.getUserInfoByLogin(login);
        final var token = jwtUtils.generateToken(user.getLogin());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/user")
    public ResponseEntity<UserServiceInfoDTO> getUserByToken(@RequestParam String token, @RequestHeader("Authorization") String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!serviceTokenUtils.validateToken(serviceToken.substring(7))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final var login = jwtUtils.getUserLoginFromToken(token);
        final var user = usersService.getUserInfoByLogin(login);

        return ResponseEntity.ok(user);
    }
}
