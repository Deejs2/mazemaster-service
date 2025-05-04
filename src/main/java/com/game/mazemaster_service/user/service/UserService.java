package com.game.mazemaster_service.user.service;

import com.game.mazemaster_service.user.dto.ChangePasswordRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.dto.UserUpdateRequest;

public interface UserService {

    UserRegistrationResponse getUserById(Long id);

    UserRegistrationResponse updateUser(UserUpdateRequest userRequest);

    String changePassword(ChangePasswordRequest changePasswordRequest);
}
