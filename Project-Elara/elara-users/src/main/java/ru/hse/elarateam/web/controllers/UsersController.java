package ru.hse.elarateam.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.dto.*;
import ru.hse.elarateam.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.dto.requests.UserRegisterRequestDTO;

import java.util.UUID;

@RequestMapping("/v1/users")
@RestController
public class UsersController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody UserRegisterRequestDTO userRegisterRequest) {

    }

    // TODO rate limiter
    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestParam String login) {

    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequestDTO resetPasswordRequest) {

        // call login service and return jwt token as response
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


    @PutMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequestDTO changePasswordRequest, @RequestHeader("Authorization") String jwtToken) {
        // check to skip barer prefix
        jwtToken = jwtToken.substring(7);

        // TODO check if old password is correct
    }
}
