package com.nepal.collegehub.user.service.implementation;

import com.nepal.collegehub.college.college.entity.College;
import com.nepal.collegehub.college.college.repository.CollegeRepository;
import com.nepal.collegehub.mail.MailService;
import com.nepal.collegehub.user.dto.request.ChangePasswordRequest;
import com.nepal.collegehub.user.dto.request.UserRegistrationRequest;
import com.nepal.collegehub.user.dto.request.UserUpdateRequest;
import com.nepal.collegehub.user.dto.response.UserRegistrationResponse;
import com.nepal.collegehub.user.role.entity.UserRole;
import com.nepal.collegehub.user.entity.UserEntity;
import com.nepal.collegehub.user.role.repository.RolesRepository;
import com.nepal.collegehub.user.repository.UserInfoRepository;
import com.nepal.collegehub.user.service.UserService;
import com.nepal.collegehub.user.messages.UserExceptionMessages;
import com.nepal.collegehub.user.messages.UserLogMessages;
import com.nepal.collegehub.user.messages.UserResponseMessages;
import com.nepal.collegehub.utils.logged_in_user.LoggedInUserUtil;
import com.nepal.collegehub.utils.password.PasswordGeneratorUtil;
import com.nepal.collegehub.utils.validator.PasswordValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserInfoRepository userInfoRepository;
    private final RolesRepository rolesRepository;
    private final CollegeRepository collegeRepository;
    private final MailService mailService;
    private final LoggedInUserUtil loggedInUserUtil;

    @Value("${frontend.domain}")
    private String frontendUrl;
    @Value("${frontend.login}")
    private String loginUrl;

    @Override
    @Transactional
    public UserRegistrationResponse registerSchoolUser(UserRegistrationRequest userRegistrationRequest) {
        College college = loggedInUserUtil.getLoggedInCollege();
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userRegistrationRequest.fullName());
        userEntity.setEmailId(userRegistrationRequest.userEmail());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(PasswordGeneratorUtil.generateStrongPassword()));
        userEntity.setRoles(
                List.of(
                        rolesRepository.findByName(UserRole.COLLEGE_USER.toString())
                                .orElseThrow(() -> new EntityNotFoundException(UserExceptionMessages.ROLE_NOT_FOUND + UserRole.COLLEGE_USER))
                )
        );
        UserEntity savedUser = userInfoRepository.save(userEntity);
        college.getCollegeUsers().add(savedUser);
        collegeRepository.save(college);

        mailService.sendUserAddedToCollegeMail(userEntity.getFullName(), college.getCollegeName(), userEntity.getEmailId(), userEntity.getPassword(), frontendUrl + loginUrl);
        log.info(UserLogMessages.USER_REGISTERED, userEntity.getEmailId());
        return new UserRegistrationResponse(userEntity);
    }

    @Override
    public Page<UserRegistrationResponse> getAllSchoolUsers(Pageable pageable) {
        College college = loggedInUserUtil.getLoggedInCollege();
        List<UserRegistrationResponse> userResponses = college.getCollegeUsers().stream()
                .map(UserRegistrationResponse::new)
                .toList();
        log.info(UserLogMessages.USERS_FETCHED_SUCCESSFULLY);
        return new PageImpl<>(userResponses, pageable, userResponses.size());
    }

    @Override
    public UserRegistrationResponse getUserById(Long id) {
        UserEntity userEntity = userInfoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(UserExceptionMessages.USER_NOT_FOUND + id)
        );
        log.info(UserLogMessages.USER_FETCHED_SUCCESSFULLY, userEntity.getEmailId());
        return new UserRegistrationResponse(userEntity);
    }

    @Override
    public UserRegistrationResponse updateUser(UserUpdateRequest userRequest) {
        UserEntity userEntity = loggedInUserUtil.getLoggedInUser();
        userEntity.setFullName(userRequest.getFullName());
        log.info(UserLogMessages.USER_UPDATED, userEntity.getEmailId());
        return new UserRegistrationResponse(userInfoRepository.save(userEntity));
    }

    @Override
    public String changePassword(ChangePasswordRequest changePasswordRequest) {
        UserEntity userEntity = loggedInUserUtil.getLoggedInUser();
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