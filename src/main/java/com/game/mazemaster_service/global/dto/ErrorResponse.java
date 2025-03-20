package com.game.mazemaster_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String error;
    private String httpStatus;

    public ErrorResponse(LocalDateTime now, String message, String error, String name) {
        this.timestamp = now;
        this.message = message;
        this.error = error;
        this.httpStatus = name;
    }
}
