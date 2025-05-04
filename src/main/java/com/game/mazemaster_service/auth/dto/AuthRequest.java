package com.game.mazemaster_service.auth.dto;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(
        @NotEmpty(message = "Email must not be empty")
        String email,
        @NotEmpty(message = "Password must not be empty")
        String password
) {
}
