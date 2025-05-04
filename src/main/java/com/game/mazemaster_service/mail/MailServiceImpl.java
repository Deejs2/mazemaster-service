package com.game.mazemaster_service.mail;

import com.game.mazemaster_service.user.entity.UserInfoEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Async
    @Override
    public void sendOtpEmail(String to, String name, String otp, LocalDateTime expiry) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedExpiry = expiry.format(formatter);
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("otp", otp);
        context.setVariable("expiryTime", formattedExpiry);

        String content = templateEngine.process("verify-otp.html", context);
        sendEmail(to, "Verify your email", content);
    }

    @Async
    @Override
    public void sendForgotPasswordMail(UserInfoEntity userEntity, String forgotPasswordUrl, LocalDateTime expiry) {
        String email = userEntity.getEmailId();
        String name = userEntity.getFullName();

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("resetLink", forgotPasswordUrl);
        context.setVariable("expiryTime", expiry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        context.setVariable("email", email);

        String content = templateEngine.process("forgot-password-otp.html", context);
        sendEmail(email, "Reset your password", content);
    }



    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("no-reply@mazemaster.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending mail", e);
        }
    }
}
