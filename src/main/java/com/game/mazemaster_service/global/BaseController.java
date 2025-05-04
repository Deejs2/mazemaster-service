package com.game.mazemaster_service.global;

import com.game.mazemaster_service.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    // Success response with data
    protected <T> ResponseEntity<ApiResponse<T>> successResponse(T data, String message, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(true);
        response.setStatusCode(status.value());
        response.setMessage(message);
        response.setData(data);
        return new ResponseEntity<>(response, status);
    }

    protected <T> ResponseEntity<ApiResponse<T>> successResponse(T data, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(true);
        response.setMessage(message);
        response.setData(data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Success response without data
    protected ResponseEntity<ApiResponse<Void>> successResponse(String message) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setStatus(true);
        response.setMessage(message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Error response
    protected ResponseEntity<ApiResponse<Void>> errorResponse(String message, String errorCode, String errorDetails, HttpStatus status) {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setStatus(false);
        response.setMessage(message);
        response.setStatusCode(status.value());

        ApiResponse.ErrorDetails error = new ApiResponse.ErrorDetails();
        error.setCode(errorCode);
        error.setDetails(errorDetails);
        response.setError(error);

        return new ResponseEntity<>(response, status);
    }

    // Error response with data
    protected <T> ResponseEntity<ApiResponse<T>> errorResponse(String message, String errorCode, String errorDetails, T data, HttpStatus status) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setStatus(false);
        response.setMessage(message);
        response.setData(data);

        ApiResponse.ErrorDetails error = new ApiResponse.ErrorDetails();
        error.setCode(errorCode);
        error.setDetails(errorDetails);
        response.setError(error);

        return new ResponseEntity<>(response, status);
    }
}
