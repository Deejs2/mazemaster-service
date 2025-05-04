package com.game.mazemaster_service.user.service;

import com.game.mazemaster_service.global.logged_in_user.LoggedInUserUtil;
import com.game.mazemaster_service.global.validator.PasswordValidator;
import com.game.mazemaster_service.user.dto.ChangePasswordRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.dto.UserUpdateRequest;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.messages.UserExceptionMessages;
import com.game.mazemaster_service.user.messages.UserLogMessages;
import com.game.mazemaster_service.user.messages.UserResponseMessages;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final LoggedInUserUtil loggedInUserUtil;

    @Value("${frontend.domain}")
    private String frontendUrl;
    @Value("${frontend.login}")
    private String loginUrl;

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