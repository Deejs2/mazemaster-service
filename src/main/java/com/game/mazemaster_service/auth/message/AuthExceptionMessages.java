package com.game.mazemaster_service.auth.message;

public class AuthExceptionMessages {
    private AuthExceptionMessages(){}
    public static final String USER_NOT_FOUND = "User not found with email: ";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String REFRESH_TOKEN_MISSING = "Refresh token is missing";
    public static final String REFRESH_TOKEN_REVOKED = "Refresh token revoked";
    public static final String TRY_AGAIN = "Please Try Again";
    public static final String USER_ALREADY_REGISTERED = "User already registered with email: ";
}
