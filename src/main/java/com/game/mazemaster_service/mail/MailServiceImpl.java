/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:2/24/2025
 * Time:4:19 PM
 */

package com.nepal.collegehub.mail;

import com.nepal.collegehub.otp.entity.OTP;
import com.nepal.collegehub.user.entity.UserEntity;
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


    @Override
    @Async
    public void sendCollegeApprovalMail(String email, String collegeName, String fullName, String password) {
        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("collegeName", collegeName);
        context.setVariable("email", email);
        context.setVariable("password", password);

        // Process the Thymeleaf template
        String emailContent = templateEngine.process("college-approval-email", context);

        // Send the email using the sendEmail method
        sendEmail(email, "Congratulations! " + collegeName + " is Approved on CollegeHub", emailContent);

        log.info("College approval email sent successfully to: {}", email);
    }

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
    public void sendUserAddedToCollegeMail(String fullName, String schoolName, String email, String password, String loginUrl) {
        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("schoolName", schoolName);
        context.setVariable("email", email);
        context.setVariable("password", password);
        context.setVariable("loginLink", loginUrl);

        String content = templateEngine.process("user-added-to-college.html", context);
        sendEmail(email, "You have been added to " + schoolName, content);
    }

    @Async
    @Override
    public void sendForgotPasswordMail(UserEntity userEntity, String forgotPasswordUrl, LocalDateTime expiry) {
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

            helper.setFrom("no-reply@collegehub.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error sending mail", e);
        }
    }
}
