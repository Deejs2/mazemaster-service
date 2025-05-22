package com.game.mazemaster_service.otp.repository;
import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByUserAndOtpValue(UserInfoEntity user, String otp);

    Optional<OTP> findByOtpValueAndPurpose(String otp, OTPPurpose purpose);
    Optional<List<OTP>> findAllByUserAndIsUsedIsFalse(UserInfoEntity userInfoEntity);
}
