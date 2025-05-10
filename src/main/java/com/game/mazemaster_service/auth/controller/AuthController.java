package com.game.mazemaster_service.auth.controller;

import com.game.mazemaster_service.auth.dto.AuthRequest;
import com.game.mazemaster_service.auth.dto.AuthResponse;
import com.game.mazemaster_service.auth.dto.ForgotPasswordResponse;
import com.game.mazemaster_service.auth.dto.ResetPasswordRequest;
import com.game.mazemaster_service.auth.message.AuthResponseMessages;
import com.game.mazemaster_service.auth.service.AuthService;
import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.user.dto.UserRegistrationRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController extends BaseController {
    private final AuthService authService;
    private final UserService userService;
    @Operation(
            summary = "User login",
            description = "Authenticates a user using email and password, then returns JWT access and refresh tokens."
    )
    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(
            @RequestBody AuthRequest authentication, HttpServletResponse response) {

        log.info("[AuthController:authenticateUser] User: {} is trying to authenticate", authentication.email());
        return successResponse(authService.getJwtTokensAfterAuthentication(authentication, response),
                AuthResponseMessages.USER_AUTHENTICATED);
    }

    @Operation(
            summary = "Forgot password",
            description = "Sends a password reset link to the provided email."
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<ForgotPasswordResponse>> forgotPassword(
            @RequestParam
            @Parameter(description = "Email address of the user", required = true, example = "user@mazemaster.com") String email) {

        return successResponse(authService.forgotPassword(email),
                AuthResponseMessages.PASSWORD_RESET_LINK_SENT);
    }

    @Operation(
            summary = "Reset password",
            description = "Resets the user's password using the token received via email."
    )
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest) {

        return successResponse(authService.resetPassword(resetPasswordRequest),
                AuthResponseMessages.PASSWORD_RESET_SUCCESSFULLY);
    }

    @Operation(
            summary = "Register User",
            description = "Registers a new user with the provided details."
    )
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> registerUser(
            @RequestBody UserRegistrationRequest registrationRequest) {

        return successResponse(userService.addUser(registrationRequest),
                AuthResponseMessages.USER_REGISTERED_SUCCESSFULLY);
    }
}
