package com.game.mazemaster_service.otp.service;

import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.otp.repository.OTPRepository;
import com.game.mazemaster_service.otp.utils.OtpUtil;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPServiceImplementation implements OTPService {

    private final OTPRepository otpRepository;
    private final OtpUtil otpUtil;

    @Value("${otp.expiryInSeconds}")
    private long expiryTime;

    @Override
    public OTP saveOTP(UserInfoEntity user, OTPPurpose purpose) {
        OTP otp = OTP.builder()
                .user(user)
                .otpValue(otpUtil.generateOtp(user.getEmailId()))
                .expiryTime(LocalDateTime.now().plusSeconds(expiryTime))
                .purpose(purpose)
                .build();
        return otpRepository.save(otp);
    }

    @Override
    public void validateOTP(UserInfoEntity user, String otp) {
        OTP otpEntity = otpRepository.findByUserAndOtpValue(user, otp)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }
        otpEntity.setIsUsed(true);
        otpRepository.save(otpEntity);
    }

    @Override
    public OTP getOTP(String otp, OTPPurpose purpose) {
        OTP userOTP = otpRepository.findByOtpValueAndPurpose(otp, purpose)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));
        if (userOTP.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }
        return userOTP;
    }

}
