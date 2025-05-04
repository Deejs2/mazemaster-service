package com.game.mazemaster_service.mail;

import com.game.mazemaster_service.user.entity.UserInfoEntity;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDateTime;

public interface MailService {

    @Async
    void sendOtpEmail(String to, String name, String otp, LocalDateTime expiry);

    @Async
    void sendForgotPasswordMail(UserInfoEntity userEntity, String forgotPasswordUrl, LocalDateTime expiry);
}
