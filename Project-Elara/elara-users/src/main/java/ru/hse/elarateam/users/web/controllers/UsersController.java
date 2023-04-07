package ru.hse.elarateam.users.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.users.dto.*;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.ResetPasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.web.services.UsersService;
import ru.hse.elarateam.users.web.services.tokens.ServiceTokenUtils;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UsersController {

    private final UsersService usersService;

    private final ServiceTokenUtils serviceTokenUtils;

    @PostMapping
    public ResponseEntity<UserInfoDTO> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequest) {
        var userInfo = usersService.saveUser(userRegisterRequest);
        return new ResponseEntity<>(userInfo, HttpStatus.CREATED);
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
            return new ResponseEntity<>("Login is not unavailable", HttpStatus.CONFLICT);
        }
    }

    // TODO rate limiter
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestParam String login) {
        usersService.forgotPassword(login);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody @Valid ResetPasswordRequestDTO resetPasswordRequest) {
        usersService.resetPassword(resetPasswordRequest);
    }

    // TODO NEED jwt token
    @PutMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequest) {
        usersService.changePassword(changePasswordRequest);
    }

    @PostMapping("/verify-email")
    public void verifyEmail(@RequestBody String verificationToken) {
        usersService.verifyEmail(verificationToken);
    }

    // TODO NEED jwt token
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestBody @Valid UserProfileUpdateRequestDTO userProfile) {
        usersService.updateUserProfile(userProfile);
    }

    // TODO NEED jwt token
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable UUID userId) {
        var userProfile = usersService.getUserProfileById(userId);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfoById(@PathVariable UUID userId, @RequestHeader("Authorization") String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer: ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean isValid = serviceTokenUtils.validateToken(serviceToken.substring(7), new EmailServiceInfo());
        if (!isValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var userInfo = usersService.getUserInfoById(userId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // TODO NEED jwt token
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable UUID userId) {
        usersService.deleteUserById(userId);
    }
}
