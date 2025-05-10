package com.game.mazemaster_service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationRequest(
        @NotEmpty(message = "Username must not be empty")
        String username,

        @Email(message = "Email must be valid")
        @NotEmpty(message = "Email must not be empty")
        String email,

        @NotEmpty(message = "Password must not be empty")
        String password
) { }