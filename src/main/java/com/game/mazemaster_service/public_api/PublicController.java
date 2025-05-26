package com.game.mazemaster_service.public_api;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.global.messages.ResponseMessageUtil;
import com.game.mazemaster_service.user.dto.UserRegistrationResponse;
import com.game.mazemaster_service.user.service.UserService;
import com.game.mazemaster_service.user_progress.dto.UserProgressResponse;
import com.game.mazemaster_service.user_progress.message.UserProgressMessage;
import com.game.mazemaster_service.user_progress.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public")
public class PublicController extends BaseController {
    private final UserProgressService userProgressService;
    private final UserService userService;

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<UserRegistrationResponse>> getUserById(@PathVariable Long id){
        return successResponse(userService.getUserById(id), ResponseMessageUtil.fetchedSuccessfully("USER"));
    }

    @GetMapping("/user-progress/{userId}")
    public ResponseEntity<ApiResponse<List<UserProgressResponse>>> getAllUserProgressByUserId(@PathVariable Long userId) {
        return successResponse(userProgressService.getAllUserProgressByUserId(userId), UserProgressMessage.USER_PROGRESS_FETCHED);
    }

}
