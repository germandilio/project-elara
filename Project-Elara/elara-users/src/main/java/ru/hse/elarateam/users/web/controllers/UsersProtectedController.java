package ru.hse.elarateam.users.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.web.services.UsersService;
import ru.hse.elarateam.users.web.services.auth.AuthenticationManager;
import ru.hse.elarateam.users.web.services.tokens.ServiceTokenUtils;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UsersProtectedController {

    private final UsersService usersService;

    private final ServiceTokenUtils serviceTokenUtils;

    private final AuthenticationManager authenticationManager;

    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequest,
                                               @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        usersService.changePassword(changePasswordRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> updateProfile(@RequestBody @Valid UserProfileUpdateRequestDTO userProfile,
                                              @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        usersService.updateUserProfile(userProfile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfileById(@PathVariable UUID userId,
                                                             @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var userProfile = usersService.getUserProfileById(userId);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId,
                                               @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        usersService.deleteUserById(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfoById(@PathVariable UUID userId,
                                                       @RequestHeader("Authorization") String serviceToken) {
        if (serviceToken == null || !serviceToken.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean isValid = serviceTokenUtils.validateToken(serviceToken.substring(7), new EmailServiceInfo());
        if (!isValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var userInfo = usersService.getUserInfoById(userId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    private boolean notAuthenticated(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return true;
        }
        try {
            authenticationManager.authenticate(token.substring(7));
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
