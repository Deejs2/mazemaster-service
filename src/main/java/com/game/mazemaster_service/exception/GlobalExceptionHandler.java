package com.game.mazemaster_service.exception;

import com.game.mazemaster_service.exception.custom.ResourceNotFoundException;
import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    // Handle MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String errorDetails = String.join(", ", errors);
        return errorResponse("Validation failed", "ERR-400", errorDetails, HttpStatus.BAD_REQUEST);
    }

    // Handle NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<Void>> handleNullPointerException(NullPointerException ex) {
        return errorResponse("Null pointer exception occurred", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return errorResponse("Illegal argument provided", "ERR-402", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handle ResourceNotFoundException (Custom Exception)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return errorResponse(ex.getMessage(), "ERR-404", "The requested resource was not found", HttpStatus.NOT_FOUND);
    }

    // Handle ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        return errorResponse(ex.getReason(), "ERR-400", ex.getMessage(), status);
    }

    // Handle Generic Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGlobalException(Exception ex) {
        return errorResponse("An unexpected error occurred", "ERR-500", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // AuthorizationDeniedException
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        return errorResponse("Access Denied", "ERR-403", ex.getMessage(), HttpStatus.FORBIDDEN);
    }
}
