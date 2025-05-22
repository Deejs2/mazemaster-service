package com.game.mazemaster_service.user_progress.controller;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.user_progress.dto.HighestLevelResponse;
import com.game.mazemaster_service.user_progress.dto.UserProgressRequest;
import com.game.mazemaster_service.user_progress.dto.UserProgressResponse;
import com.game.mazemaster_service.user_progress.message.UserProgressMessage;
import com.game.mazemaster_service.user_progress.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-progress")
public class UserProgressController extends BaseController {
    private final UserProgressService userProgressService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<UserProgressResponse>> saveUserProgress(@RequestBody UserProgressRequest userProgressRequest) {
        return successResponse(userProgressService.saveUserProgress(userProgressRequest), UserProgressMessage.USER_PROGRESS_SAVED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<UserProgressResponse>>> getAllUserProgressByUserId(@PathVariable Long userId) {
        return successResponse(userProgressService.getAllUserProgressByUserId(userId), UserProgressMessage.USER_PROGRESS_FETCHED);
    }

    @GetMapping("/maze/{mazeId}")
    public ResponseEntity<ApiResponse<List<UserProgressResponse>>> getAllUserProgressByMazeId(@PathVariable Long mazeId) {
        return successResponse(userProgressService.getAllUserProgressByMazeId(mazeId), UserProgressMessage.USER_PROGRESS_FETCHED);
    }

    @GetMapping("/{userId}/{mazeId}")
    public ResponseEntity<ApiResponse<UserProgressResponse>> getUserProgress(@PathVariable Long userId, @PathVariable Long mazeId) {
        return successResponse(userProgressService.getUserProgress(userId, mazeId), UserProgressMessage.USER_PROGRESS_FETCHED);
    }

    @DeleteMapping("/{userId}/{mazeId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserProgress(@PathVariable Long userId, @PathVariable Long mazeId) {
        userProgressService.deleteUserProgress(userId, mazeId);
        return successResponse(null, UserProgressMessage.USER_PROGRESS_DELETED);
    }

    @GetMapping("/highest-level")
    public ResponseEntity<ApiResponse<HighestLevelResponse>> getHighestLevelByCategory(@RequestParam (defaultValue = "EASY") LevelCategory category) {
        return successResponse(userProgressService.getHighestLevelByCategory(category), UserProgressMessage.HIGHEST_LEVEL_FETCHED);
    }
}
