package com.game.mazemaster_service.otp.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class OtpUtil {

    private static final String DIGITS = "0123456789";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final OtpProperties otpProperties;
    private static final Map<String, OtpData> otpStore = new HashMap<>();

    public OtpUtil(OtpProperties otpProperties) {
        this.otpProperties = otpProperties;
    }

    /**
     * Generates a secure OTP using values from application.yml
     * @param key The key to identify the OTP (e.g., user email or phone)
     * @return Generated OTP
     */
    public String generateOtp(String key) {
        String characters = otpProperties.isAlphanumeric() ? ALPHANUMERIC : DIGITS;
        int length = otpProperties.getLength();
        int expiryInSeconds = otpProperties.getExpiryInSeconds();

        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(characters.charAt(RANDOM.nextInt(characters.length())));
        }

        String generatedOtp = otp.toString();
        long expiryTime = Instant.now().getEpochSecond() + expiryInSeconds;

        // Store OTP with expiry
        otpStore.put(key, new OtpData(generatedOtp, expiryTime));

        return generatedOtp;
    }

    /**
     * Validates the OTP for a given key.
     * @param key The key to identify the OTP.
     * @param otp The OTP to validate.
     * @return true if valid, false otherwise.
     */
    public boolean validateOtp(String key, String otp) {
        if (!otpStore.containsKey(key)) {
            return false; // OTP not found
        }

        OtpData otpData = otpStore.get(key);
        if (Instant.now().getEpochSecond() > otpData.expiryTime) {
            otpStore.remove(key); // Expired OTP, remove it
            return false;
        }

        boolean isValid = otpData.otp.equals(otp);
        if (isValid) {
            otpStore.remove(key); // OTP is used, remove it
        }

        return isValid;
    }

    // Internal class to store OTP data
    private static class OtpData {
        String otp;
        long expiryTime;

        OtpData(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }
    }
}