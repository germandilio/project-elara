package ru.hse.elarateam.users.web.controllers;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    /**
     * Register new user
     *
     * @param userRegisterRequest user info
     * @return registered user info with id
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered"),
            @ApiResponse(responseCode = "400", description = "Invalid user info",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Login is unavailable",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping
    public ResponseEntity<UserInfoDTO> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequest) {
        var userInfo = usersService.saveUser(userRegisterRequest);
        return new ResponseEntity<>(userInfo, HttpStatus.CREATED);
    }

    /**
     * Check if login is available
     *
     * @param login login to check (should be valid email)
     * @return status indicating if login is available
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login is available"),
            @ApiResponse(responseCode = "400", description = "Invalid login",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "409", description = "Login is unavailable",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
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

    /**
     * Send email with password reset link (forgot password feature)
     *
     * @param login user login (should be valid email)
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Email sent"),
            @ApiResponse(responseCode = "400", description = "Invalid login",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void forgotPassword(@RequestParam @NotNull @Size(min = 1, max = 128) @Email String login) {
        usersService.forgotPassword(login);
    }

    /**
     * Reset password with reset token.
     *
     * @param resetPasswordRequest
     * @apiNote Method should be called after user clicked on link in email.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password reset"),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody @Valid ResetPasswordRequestDTO resetPasswordRequest) {
        usersService.resetPassword(resetPasswordRequest);
    }

    /**
     * Verify email with verification token.
     *
     * @param verificationToken verification token
     * @apiNote Method should be called after user clicked on link in email.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Email verified"),
            @ApiResponse(responseCode = "400", description = "Invalid token",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/verify-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verifyEmail(@RequestParam("token") @NotNull @Size(min = 1, max = 128) String verificationToken) {
        usersService.verifyEmail(verificationToken);
    }
}
