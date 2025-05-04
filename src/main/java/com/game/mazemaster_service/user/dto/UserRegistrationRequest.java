package com.game.mazemaster_service.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationRequest(
        String fullName,

        @NotEmpty(message = "User email must not be empty") // Neither null nor 0 size
        @Email(message = "Invalid email format")
        String userEmail
) { }