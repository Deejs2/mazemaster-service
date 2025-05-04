package com.game.mazemaster_service.otp.service;

import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.user.entity.UserInfoEntity;

public interface OTPService {
    OTP saveOTP(UserInfoEntity user, OTPPurpose purpose);

    void validateOTP(UserInfoEntity user, String otp);

    OTP getOTP(String otp, OTPPurpose purpose);
}
