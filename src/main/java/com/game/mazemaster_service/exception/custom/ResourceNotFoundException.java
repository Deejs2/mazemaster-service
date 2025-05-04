package com.nepal.collegehub.exceptions.custom;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final String details;

    public ResourceNotFoundException(String message, String details) {
        super(message);
        this.details = details;
    }

}