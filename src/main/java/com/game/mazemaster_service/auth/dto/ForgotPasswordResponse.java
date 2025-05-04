package com.game.mazemaster_service.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    private String message;
    private String email;
    private LocalDateTime expiresAt;

}
