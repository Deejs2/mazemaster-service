package com.game.mazemaster_service.global;

import com.game.mazemaster_service.global.dto.ErrorResponse;
import com.game.mazemaster_service.global.dto.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class BaseController {
    public ResponseEntity<SuccessResponse> successResponse(Object data) {
        SuccessResponse response = new SuccessResponse(LocalDateTime.now(), "success",
                data, HttpStatus.OK.name());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<SuccessResponse> successResponse(Object data, String message){
        SuccessResponse response = new SuccessResponse(
                LocalDateTime.now(),
                message,
                data,
                HttpStatus.OK.name()
        );
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<ErrorResponse> errorResponse(HttpStatus status, String message, Exception exception) {
        ErrorResponse response = new ErrorResponse(LocalDateTime.now(), message, exception.getMessage(), status.name());
        response.setMessage(message);
        response.setError(exception.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
