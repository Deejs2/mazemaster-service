package com.nepal.collegehub.auth.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record AuthRequest(
        @NotEmpty(message = "Email must not be empty")
        String email,
        @NotEmpty(message = "Password must not be empty")
        String password
) {
}
