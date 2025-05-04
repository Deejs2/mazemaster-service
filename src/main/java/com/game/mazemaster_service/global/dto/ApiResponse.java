package com.game.mazemaster_service.global.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean status;
    private int statusCode = 200;
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private T data;
    private ErrorDetails error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetails {
        private String code;
        private String details;
    }
}