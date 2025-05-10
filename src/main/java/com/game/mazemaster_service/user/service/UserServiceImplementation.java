package com.game.mazemaster_service.user.service;

import com.game.mazemaster_service.global.logged_in_user.LoggedInUserUtil;
import com.game.mazemaster_service.global.validator.PasswordValidator;
import com.game.mazemaster_service.mail.MailService;
import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.otp.service.OTPService;
import com.game.mazemaster_service.user.dto.ChangePasswordRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.dto.UserUpdateRequest;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.messages.UserExceptionMessages;
import com.game.mazemaster_service.user.messages.UserLogMessages;
import com.game.mazemaster_service.user.messages.UserResponseMessages;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import com.game.mazemaster_service.user.role.entity.UserRole;
import com.game.mazemaster_service.user.role.repository.RolesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final LoggedInUserUtil loggedInUserUtil;
    private final OTPService otpService;
    private final MailService mailService;
    private final RolesRepository rolesRepository;

    @Override
    public UserRegistrationResponse addUser(UserRegistrationRequest userRegistrationRequest) {
        log.info(UserLogMessages.USER_REGISTERED, userRegistrationRequest.email());

        if (userInfoRepository.existsByEmailId(userRegistrationRequest.email())) {
            throw new IllegalArgumentException(UserExceptionMessages.USER_ALREADY_EXISTS + userRegistrationRequest.email());
        }

        UserInfoEntity userEntity = new UserInfoEntity();
        userEntity.setEmailId(userRegistrationRequest.email());
        userEntity.setFullName(userRegistrationRequest.username());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(userRegistrationRequest.password()));
        userEntity.setRoles(List.of(
                rolesRepository.findByName(UserRole.ROLE_PLAYER.toString()).orElseThrow(
                        () -> new RuntimeException("ROLE_PLAYER not found")
                )
        ));
        userEntity.setVerified(false);

        userInfoRepository.save(userEntity);
        log.info(UserLogMessages.USER_REGISTERED_SUCCESSFULLY, userEntity.getEmailId());

        OTP otp = otpService.saveOTP(userEntity, OTPPurpose.REGISTER);
        mailService.sendOtpEmail(userEntity.getEmailId(), userEntity.getFullName(), otp.getOtpValue(), otp.getExpiryTime());
        log.info(UserLogMessages.OTP_SENT, userEntity.getEmailId());

        return new UserRegistrationResponse(userEntity);
    }

    @Override
    public UserRegistrationResponse getUserById(Long id) {
        UserInfoEntity userEntity = userInfoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(UserExceptionMessages.USER_NOT_FOUND + id)
        );
        log.info(UserLogMessages.USER_FETCHED_SUCCESSFULLY, userEntity.getEmailId());
        return new UserRegistrationResponse(userEntity);
    }

    @Override
    public UserRegistrationResponse updateUser(UserUpdateRequest userRequest) {
        UserInfoEntity userEntity = loggedInUserUtil.getLoggedInUser();
        userEntity.setFullName(userRequest.getFullName());
        log.info(UserLogMessages.USER_UPDATED, userEntity.getEmailId());
        return new UserRegistrationResponse(userInfoRepository.save(userEntity));
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        UserInfoEntity userEntity = loggedInUserUtil.getLoggedInUser();
        if (!PasswordValidator.isValid(changePasswordRequest.getNewPassword())) {
            throw new IllegalArgumentException(UserExceptionMessages.INVALID_PASSWORD);
        }
        if (!new BCryptPasswordEncoder().matches(changePasswordRequest.getOldPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException(UserExceptionMessages.INCORRECT_OLD_PASSWORD);
        }
        userEntity.setPassword(new BCryptPasswordEncoder().encode(changePasswordRequest.getNewPassword()));
        userInfoRepository.save(userEntity);
        log.info(UserLogMessages.PASSWORD_CHANGED, userEntity.getEmailId());
        return UserResponseMessages.PASSWORD_CHANGED_SUCCESSFULLY;
    }
}