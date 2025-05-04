package com.game.mazemaster_service.auth.service;

import com.game.mazemaster_service.auth.dto.AuthRequest;
import com.game.mazemaster_service.auth.dto.AuthResponse;
import com.game.mazemaster_service.auth.dto.ForgotPasswordResponse;
import com.game.mazemaster_service.auth.dto.ResetPasswordRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CookieValue;

public interface AuthService {
    AuthResponse getJwtTokensAfterAuthentication(AuthRequest authenticationRequest, HttpServletResponse response);

    AuthResponse getAccessTokenUsingRefreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken);

    ForgotPasswordResponse forgotPassword(String email);

    String resetPassword(ResetPasswordRequest resetPasswordRequest);
}
