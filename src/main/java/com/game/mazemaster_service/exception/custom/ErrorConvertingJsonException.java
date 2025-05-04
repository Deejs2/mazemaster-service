package com.game.mazemaster_service.exception.custom;
public class ErrorConvertingJsonException extends RuntimeException {

    public ErrorConvertingJsonException(String message) {
        super(message);
    }
}