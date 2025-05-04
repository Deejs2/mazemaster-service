/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:2/24/2025
 * Time:4:38 PM
 */

package com.nepal.collegehub.otp.repository;

import com.nepal.collegehub.otp.entity.OTP;
import com.nepal.collegehub.otp.entity.OTPPurpose;
import com.nepal.collegehub.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByUserAndOtpValue(UserEntity user, String otp);

    Optional<OTP> findByOtpValueAndPurpose(String otp, OTPPurpose purpose);
}
