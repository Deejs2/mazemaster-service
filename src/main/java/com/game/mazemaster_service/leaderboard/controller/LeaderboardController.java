package com.game.mazemaster_service.leaderboard.controller;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.leaderboard.dto.LeaderboardResponse;
import com.game.mazemaster_service.leaderboard.message.LeaderboardMessage;
import com.game.mazemaster_service.leaderboard.service.LeaderboardService;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leaderboard")
public class LeaderboardController extends BaseController {
    private final LeaderboardService leaderboardService;

    @GetMapping("/global")
    public ResponseEntity<ApiResponse<Page<LeaderboardResponse>>> getGlobalLeaderboard(Pageable pageable) {
        return successResponse(leaderboardService.getGlobalLeaderboard(pageable), LeaderboardMessage.GLOBAL_LEADERBOARD_SUCCESS);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<Page<LeaderboardResponse>>> getCategoryLeaderboard(@PathVariable LevelCategory category, Pageable pageable) {
        return successResponse(leaderboardService.getGlobalLeaderboard(category, pageable), LeaderboardMessage.GLOBAL_LEADERBOARD_SUCCESS);
    }

    @GetMapping("/rank/{userId}")
    public ResponseEntity<ApiResponse<Integer>> getUserRank(@PathVariable Long userId) {
        return successResponse(leaderboardService.getUserRank(userId), LeaderboardMessage.GLOBAL_LEADERBOARD_SUCCESS);
    }
}
