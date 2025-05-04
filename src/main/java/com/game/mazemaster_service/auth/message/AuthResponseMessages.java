package com.game.mazemaster_service.auth.message;

public class AuthResponseMessages {
    private AuthResponseMessages() {}

    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_AUTHENTICATED = "User authenticated";
    public static final String USER_LOGGED_OUT = "User logged out";
    public static final String ACCESS_TOKEN_REFRESHED = "Access token refreshed";
    public static final String PASSWORD_RESET_LINK_SENT = "Password reset link sent";
    public static final String PASSWORD_RESET_SUCCESSFULLY = "Password reset successfully";
    public static final String PASSWORD_RESET_SUCCESS = "Password reset successfully";
}
