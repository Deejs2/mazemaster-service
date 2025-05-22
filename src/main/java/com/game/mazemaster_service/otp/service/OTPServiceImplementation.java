package com.game.mazemaster_service.otp.service;

import com.game.mazemaster_service.mail.MailService;
import com.game.mazemaster_service.otp.dto.OtpValidateRequest;
import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.otp.repository.OTPRepository;
import com.game.mazemaster_service.otp.utils.OtpUtil;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.messages.UserExceptionMessages;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPServiceImplementation implements OTPService {

    private final UserInfoRepository userInfoRepository;
    private final OTPRepository otpRepository;
    private final OtpUtil otpUtil;
    private final MailService mailService;

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
    public String validateOTP(OtpValidateRequest otpValidateRequest) {

        UserInfoEntity user = userInfoRepository.findByEmailId(otpValidateRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(UserExceptionMessages.USER_NOT_FOUND));

        OTP otpEntity = otpRepository.findByUserAndOtpValue(user, otpValidateRequest.getOtp())
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP has expired");
        }
        otpEntity.setIsUsed(true);
        otpRepository.save(otpEntity);

        return "OTP validated successfully";
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

    @Override
    public String resendOtp(String email) {
        log.info("[OTPService:resendOtp] Resending OTP to email: {}", email);
        UserInfoEntity user = userInfoRepository.findByEmailId(email)
                .orElseThrow(() -> new IllegalArgumentException(UserExceptionMessages.USER_NOT_FOUND));

        List<OTP> otpList = otpRepository.findAllByUserAndIsUsedIsFalse(user)
                .orElseThrow(() -> new IllegalArgumentException("No OTP found for user"));
        for (OTP otp : otpList) {
            otp.setIsUsed(true);
            otpRepository.save(otp);
        }

        String newOtp = otpUtil.generateOtp(email);
        OTP otp = new OTP();
        otp.setOtpValue(newOtp);
        otp.setExpiryTime(LocalDateTime.now().plusSeconds(expiryTime));
        otp.setIsUsed(false);
        otp.setUser(user);
        otp.setPurpose(OTPPurpose.REGISTER);
        otpRepository.save(otp);

        // Send the new OTP to the user's email
        mailService.sendOtpEmail(user.getEmailId(), user.getFullName(), newOtp, otp.getExpiryTime());
        log.info("[OTPService:resendOtp] New OTP sent to email: {}", email);

        return "OTP resent successfully";
    }

}
