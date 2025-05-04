package com.game.mazemaster_service.global.validator;

import java.util.regex.Pattern;

public class PhoneValidator {
    private PhoneValidator() {}

    // Regex pattern for Nepalese mobile and landline numbers
    private static final String PHONE_PATTERN =
            "^(?:\\+977[- ]?)?" +               // Optional country code (+977, +977-)
                    "(?:" +
                    "(97|98)\\d{8}|" +                  // Mobile numbers: 10 digits (97xxxxxxxx, 98xxxxxxxx)
                    "(1\\d{6,7})|" +                    // Kathmandu landline: 7-8 digits (01xxxxxxx)
                    "([2-9]\\d{7})" +                    // Other landlines: 8 digits (0XXxxxxxxx)
                    ")$";

    private static final Pattern pattern = Pattern.compile(PHONE_PATTERN);

    public static boolean isValid(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return pattern.matcher(phone.trim()).matches();
    }
}
