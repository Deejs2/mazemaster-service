package com.nepal.collegehub.user.service;

import com.nepal.collegehub.user.dto.request.ChangePasswordRequest;
import com.nepal.collegehub.user.dto.request.UserRegistrationRequest;
import com.nepal.collegehub.user.dto.request.UserUpdateRequest;
import com.nepal.collegehub.user.dto.response.UserRegistrationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserRegistrationResponse registerSchoolUser(UserRegistrationRequest userRegistrationRequest);

    Page<UserRegistrationResponse> getAllSchoolUsers(Pageable pageable);

    UserRegistrationResponse getUserById(Long id);

    UserRegistrationResponse updateUser(UserUpdateRequest userRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);
}
