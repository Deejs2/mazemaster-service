package com.game.mazemaster_service.exception.custom;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String details;

    public ResourceNotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }

}