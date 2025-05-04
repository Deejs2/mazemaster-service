package com.game.mazemaster_service.message;

public class SystemMessage {
    private SystemMessage() {}
    public static final String ERROR_CONVERTING_JSON_MESSAGE = "Error converting JSON message";

    // Maze Messages
    public static final String MAZE_FETCHED_SUCCESSFULLY = "Maze fetched successfully";

    // Exception Messages
    public static final String VALIDATION_FAILED_MESSAGE = "Validation failed";
    public static final String NULL_POINTER_EXCEPTION_MESSAGE = "Null pointer exception";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "Illegal argument exception";
    public static final String AUTHORIZATION_DENIED_MESSAGE = "Authorization denied";
}
