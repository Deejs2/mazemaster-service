package com.game.mazemaster_service.user.controller;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.global.messages.ResponseMessageUtil;
import com.game.mazemaster_service.user.dto.ChangePasswordRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationRequest;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.dto.UserUpdateRequest;
import com.game.mazemaster_service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {

    private static final String USER = "User";
    private final UserService userService;
    @PostMapping()
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> addUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        return successResponse(userService.addUser(userRegistrationRequest), ResponseMessageUtil.createdSuccessfully(USER));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> getUserById(@PathVariable Long id){
        return successResponse(userService.getUserById(id), ResponseMessageUtil.fetchedSuccessfully(USER));
    }
    @PutMapping()
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> updateUser(@RequestBody UserUpdateRequest userRequest){
        return successResponse(userService.updateUser(userRequest), ResponseMessageUtil.updatedSuccessfully(USER));
    }
    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return successResponse(userService.changePassword(changePasswordRequest), "Password changed successfully");
    }
}