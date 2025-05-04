/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/6/2025
 * Time:12:32 PM
 */

package com.nepal.collegehub.auth.messages;

public class AuthLogMessages {
    private AuthLogMessages() {}
    public static final String USER_NOT_FOUND = "[AuthService:userSignInAuth] User :{} not found";
    public static final String INVALID_CREDENTIALS = "[AuthService:userSignInAuth] Invalid credentials for user: {}";
    public static final String ACCESS_TOKEN_GENERATED = "[AuthService:userSignInAuth] Access token for user:{}, has been generated";
    public static final String EXCEPTION_AUTHENTICATING = "[AuthService:userSignInAuth] Exception while authenticating the user due to : {}";

}
