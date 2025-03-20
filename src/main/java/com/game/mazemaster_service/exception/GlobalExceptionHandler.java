package com.game.mazemaster_service.exception;

import com.game.mazemaster_service.global.dto.ErrorResponse;
import com.game.mazemaster_service.message.SystemMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ErrorConvertingJsonException.class)
    public ErrorResponse handleException(ErrorConvertingJsonException e) {
        return new ErrorResponse(LocalDateTime.now(), SystemMessage.ERROR_CONVERTING_JSON_MESSAGE, e.getMessage(), "400");
    }
}
