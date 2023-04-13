package ru.hse.elarateam.users.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.elarateam.users.dto.UpdateProfileResponse;
import ru.hse.elarateam.users.dto.UserDTO;
import ru.hse.elarateam.users.dto.UserInfoDTO;
import ru.hse.elarateam.users.dto.UserProfileDTO;
import ru.hse.elarateam.users.dto.requests.ChangePasswordRequestDTO;
import ru.hse.elarateam.users.dto.requests.UserProfileUpdateRequestDTO;
import ru.hse.elarateam.users.web.services.UsersService;
import ru.hse.elarateam.users.web.services.auth.AuthenticationManager;
import ru.hse.elarateam.users.web.services.tokens.ServiceTokenUtils;
import ru.hse.elarateam.users.web.services.tokens.emailservice.EmailServiceInfo;
import ru.hse.elarateam.users.web.services.tokens.jwt.JWTUtils;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/v1/users")
@RestController
public class UsersProtectedController {

    private final UsersService usersService;

    private final ServiceTokenUtils serviceTokenUtils;

    private final AuthenticationManager authenticationManager;

    private final JWTUtils jwtUtils;

    /**
     * Change password request.
     * <p>
     * Note: This method is protected by JWT token.
     *
     * @param changePasswordRequest request with old and new password
     * @param token                 JWT token
     * @return status indicating if password was changed
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password changed"),
            @ApiResponse(responseCode = "400", description = "Invalid password",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequest,
                                               @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        usersService.changePassword(changePasswordRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update user profile.
     * <p>
     * Note: This method is protected by JWT token.
     *
     * @param userProfile user profile to update
     * @param token       JWT token
     * @return status indicating if profile was updated
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile updated"),
            @ApiResponse(responseCode = "400", description = "Invalid profile",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping
    public ResponseEntity<UpdateProfileResponse> updateProfile(@RequestBody @Valid UserProfileUpdateRequestDTO userProfile,
                                                               @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        final var user = usersService.updateUserProfile(userProfile);
        final var newToken = jwtUtils.generateToken(user.getEmail());

        final var response = UpdateProfileResponse.builder()
                .token(newToken)
                .user(user)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    /**
     * Get user profile by id.
     * <p>
     * Note: This method is protected by JWT token.
     *
     * @param userId user id
     * @param token  JWT token
     * @return user profile, id and role
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found",
                    content = @Content(schema = @Schema(implementation = UserProfileDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Profile not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getUserProfileById(@PathVariable UUID userId,
                                                      @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var user = usersService.getUserProfileById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Delete user by id.
     * <p>
     * Note: This method is protected by JWT token.
     *
     * @param userId user id
     * @param token  JWT token
     * @return status indicating if user was deleted
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId,
                                               @RequestHeader("Authorization") String token) {
        if (notAuthenticated(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        usersService.deleteUserById(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Get user info by id.
     * <p>
     * Note: This method is service (protected by service token).
     *
     * @param userId       user id
     * @param serviceToken service token
     * @return user info
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info found",
                    content = @Content(schema = @Schema(implementation = UserInfoDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User info not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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
