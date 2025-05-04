/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/4/2025
 * Time:4:37 PM
 */

package com.nepal.collegehub.utils.validator;

import java.util.regex.Pattern;

public class PasswordValidator {
    private PasswordValidator() {}

    private static final String PASSWORD_PATTERN =
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();
    }
}