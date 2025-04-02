package com.game.mazemaster_service.auth.service;

import com.game.mazemaster_service.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    AuthResponse getJwtTokensAfterAuthentication(Authentication authentication);
}
