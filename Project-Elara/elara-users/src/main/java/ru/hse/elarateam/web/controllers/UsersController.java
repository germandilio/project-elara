package ru.hse.elarateam.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.dto.*;
import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.web.services.UsersService;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UsersController {

    private final UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterRequestDTO userRegisterRequest) {

    }

    @PostMapping("/login-available")
    public ResponseEntity<String> checkLoginAvailability(@RequestParam String login) {
        if (login == null || login.isEmpty()) {
            return new ResponseEntity<>("Login is empty", HttpStatus.BAD_REQUEST);
        }

        boolean isAvailable = usersService.checkLoginAvailability(login);
        if (isAvailable) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Login is not available", HttpStatus.CONFLICT);
        }
    }

    // TODO rate limiter
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam String login) {

    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestParam String token) {

        // call login service and return jwt token as response
    }

    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequest, @RequestHeader("Authorization") String jwtToken) {
        // check to skip barer prefix
        jwtToken = jwtToken.substring(7);

        // TODO check if old password is correct
    }

    @PostMapping("/verify-email")
    public void verifyEmail(@RequestBody String verificationToken) {

    }

    @PutMapping
    public void updateProfile(@RequestBody UserProfileUpdateRequestDTO userProfile, @RequestHeader("Authorization") String jwtToken) {

    }

    @GetMapping("/profile/{userId}")
    public UserProfileDTO getUserProfileById(@PathVariable UUID userId, @RequestHeader("Authorization") String jwtToken) {

    }

    @GetMapping("/{userId}")
    public UserInfoDTO getUserInfoById(@PathVariable UUID userId, @RequestHeader("Authorization") String jwtToken) {
        // TODO check service token and return user info
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable UUID userId, @RequestHeader("Authorization") String jwtToken) {
        // soft delete

    }
}
