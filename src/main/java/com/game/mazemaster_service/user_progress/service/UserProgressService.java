package com.game.mazemaster_service.user_progress.service;

import com.game.mazemaster_service.user_progress.dto.UserProgressRequest;
import com.game.mazemaster_service.user_progress.dto.UserProgressResponse;

import java.util.List;

public interface UserProgressService {
    UserProgressResponse saveUserProgress(UserProgressRequest userProgressRequest);

    UserProgressResponse getUserProgress(Long userId, Long mazeId);

    List<UserProgressResponse> getAllUserProgressByMazeId(Long mazeId);

    List<UserProgressResponse> getAllUserProgressByUserId(Long userId);

    void deleteUserProgress(Long userId, Long mazeId);
}
