package com.game.mazemaster_service.user.controller;

import com.nepal.collegehub.common.BaseController;
import com.nepal.collegehub.response.ApiResponse;
import com.nepal.collegehub.user.dto.request.ChangePasswordRequest;
import com.nepal.collegehub.user.dto.request.UserRegistrationRequest;
import com.nepal.collegehub.user.dto.request.UserUpdateRequest;
import com.nepal.collegehub.user.dto.response.UserRegistrationResponse;
import com.nepal.collegehub.user.messages.UserSwaggerDocumentationMessage;
import com.nepal.collegehub.user.service.UserService;
import com.nepal.collegehub.utils.messages.ResponseMessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nepal.collegehub.user.messages.UserAPIConstants.API_USER;
import static com.nepal.collegehub.user.messages.UserAPIConstants.CHANGE_PASSWORD;

@RestController
@RequestMapping(API_USER)
@RequiredArgsConstructor
@Slf4j
public class UserController extends BaseController {

    private static final String USER = "User";
    private final UserService userService;

    @Operation(
            summary = UserSwaggerDocumentationMessage.ADD_USER_SUMMARY,
            description = UserSwaggerDocumentationMessage.ADD_USER_DESCRIPTION
    )
    @PostMapping()
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> addUser(@RequestBody UserRegistrationRequest userRequest){
        return successResponse(userService.registerSchoolUser(userRequest), ResponseMessageUtil.createdSuccessfully(USER));
    }

    @Operation(
            summary = UserSwaggerDocumentationMessage.GET_ALL_SCHOOL_USERS_SUMMARY,
            description = UserSwaggerDocumentationMessage.GET_ALL_SCHOOL_USERS_DESCRIPTION
    )
    @GetMapping("/school")
    public ResponseEntity<ApiResponse<Page<UserRegistrationResponse>>> getAllSchoolUsers(Pageable pageable){
        return successResponse(userService.getAllSchoolUsers(pageable), ResponseMessageUtil.allFetchedSuccessfully(USER));
    }

    @Operation(
            summary = UserSwaggerDocumentationMessage.GET_USER_BY_ID_SUMMARY,
            description = UserSwaggerDocumentationMessage.GET_USER_BY_ID_DESCRIPTION
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> getUserById(@PathVariable Long id){
        return successResponse(userService.getUserById(id), ResponseMessageUtil.fetchedSuccessfully(USER));
    }

    @Operation(
            summary = UserSwaggerDocumentationMessage.UPDATE_USER_SUMMARY,
            description = UserSwaggerDocumentationMessage.UPDATE_USER_DESCRIPTION
    )
    @PutMapping()
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> updateUser(@RequestBody UserUpdateRequest userRequest){
        return successResponse(userService.updateUser(userRequest), ResponseMessageUtil.updatedSuccessfully(USER));
    }

    @Operation(
            summary = UserSwaggerDocumentationMessage.CHANGE_PASSWORD_SUMMARY,
            description = UserSwaggerDocumentationMessage.CHANGE_PASSWORD_DESCRIPTION
    )
    @PutMapping(CHANGE_PASSWORD)
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return successResponse(userService.changePassword(changePasswordRequest), "Password changed successfully");
    }
}