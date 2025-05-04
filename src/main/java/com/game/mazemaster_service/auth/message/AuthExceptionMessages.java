/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/6/2025
 * Time:12:36 PM
 */

package com.nepal.collegehub.auth.messages;

public class AuthExceptionMessages {
    private AuthExceptionMessages(){}
    public static final String USER_NOT_FOUND = "User not found with email: ";
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String REFRESH_TOKEN_MISSING = "Refresh token is missing";
    public static final String REFRESH_TOKEN_REVOKED = "Refresh token revoked";
    public static final String TRY_AGAIN = "Please Try Again";
}
