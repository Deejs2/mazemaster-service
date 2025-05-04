package com.nepal.collegehub.mail;

import com.nepal.collegehub.otp.entity.OTP;
import com.nepal.collegehub.user.entity.UserEntity;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public interface MailService {

    @Async
    void sendCollegeApprovalMail(String email, String collegeName, String fullName, String password);

    @Async
    void sendOtpEmail(String to, String name, String otp, LocalDateTime expiry);



    @Async
    void sendUserAddedToCollegeMail(String fullName, String schoolName, String email, String password, String loginUrl);

    @Async
    void sendForgotPasswordMail(UserEntity userEntity, String forgotPasswordUrl, LocalDateTime expiry);
}
