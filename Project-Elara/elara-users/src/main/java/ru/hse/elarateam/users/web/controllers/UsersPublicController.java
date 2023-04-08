package ru.hse.elarateam.users.web.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.web.services.UsersService;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UsersPublicController {
    private final UsersService usersService;

    @PostMapping
    public ResponseEntity<UserInfoDTO> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequest) {
        var userInfo = usersService.saveUser(userRegisterRequest);
        return new ResponseEntity<>(userInfo, HttpStatus.CREATED);
    }

    @GetMapping("/login-available")
    public ResponseEntity<String> checkLoginAvailability(@RequestParam @NotNull @Email String login) {
        if (login == null || login.isEmpty()) {
            return new ResponseEntity<>("Login is empty", HttpStatus.BAD_REQUEST);
        }

        boolean isAvailable = usersService.checkLoginAvailability(login);
        if (isAvailable) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login is not unavailable", HttpStatus.CONFLICT);
        }
    }

    // TODO rate limiter
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestParam @NotNull @Size(min = 1, max = 128) @Email String login) {
        usersService.forgotPassword(login);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody @Valid ResetPasswordRequestDTO resetPasswordRequest) {
        usersService.resetPassword(resetPasswordRequest);
    }

    @PostMapping("/verify-email")
    public void verifyEmail(@RequestParam("token") @NotNull @Size(min = 1, max = 128) String verificationToken) {
        usersService.verifyEmail(verificationToken);
    }


}
