package ru.hse.elarateam.login.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    /**
     * Perform login operation.
     * @param login user login (should match email validation rules)
     * @param password user password
     * @return JWT token if login and password are correct
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Login or password is incorrect",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Login or password is not provided",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Login service is unavailable",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam @NotNull @Email String login, @RequestParam @NotNull String password) {
        final var user = usersService.login(login, password);
        final var token = jwtUtils.generateToken(user.getLogin());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Get user info by token.
     * @param token JWT token
     * @param serviceToken service token
     * @return user info
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return user info"),
            @ApiResponse(responseCode = "401", description = "Invalid service token",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Token is not provided",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Login service is unavailable",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/user")
    public ResponseEntity<UserServiceInfoDTO> getUserByToken(@RequestParam String token, @RequestHeader("Authorization") String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!serviceTokenUtils.validateToken(serviceToken.substring(7))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            final var login = jwtUtils.getUserLoginFromToken(token);
            final var user = usersService.findUserByLogin(login);
            return ResponseEntity.ok(user);

        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
