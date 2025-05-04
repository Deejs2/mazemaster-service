package com.game.mazemaster_service.auth.controller;

import com.game.mazemaster_service.auth.dto.AuthRequest;
import com.game.mazemaster_service.auth.dto.AuthResponse;
import com.game.mazemaster_service.auth.dto.ForgotPasswordResponse;
import com.game.mazemaster_service.auth.dto.ResetPasswordRequest;
import com.game.mazemaster_service.auth.message.AuthResponseMessages;
import com.game.mazemaster_service.auth.service.AuthService;
import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
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
            summary = "Refresh JWT access token",
            description = "Generates a new access token using a valid refresh token."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> getAccessToken(
            @CookieValue(name = "refresh_token", required = false)
            @Parameter(description = "Refresh token stored in cookies") String refreshToken) {

        return successResponse(authService.getAccessTokenUsingRefreshToken(refreshToken),
                AuthResponseMessages.ACCESS_TOKEN_REFRESHED);
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
}
