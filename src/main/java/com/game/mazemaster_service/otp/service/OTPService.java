package com.nepal.collegehub.otp.service;

import com.nepal.collegehub.otp.entity.OTP;
import com.nepal.collegehub.otp.entity.OTPPurpose;
import com.nepal.collegehub.user.entity.UserEntity;

public interface OTPService {
    OTP saveOTP(UserEntity user, OTPPurpose purpose);

    void validateOTP(UserEntity user, String otp);

    OTP getOTP(String otp, OTPPurpose purpose);
}
