package com.game.mazemaster_service.leaderboard.service;

import com.game.mazemaster_service.leaderboard.dto.LeaderboardResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaderboardService {
    Page<LeaderboardResponse> getGlobalLeaderboard(Pageable pageable);
    Page<LeaderboardResponse> getGlobalLeaderboard(LevelCategory category, Pageable pageable);
    Integer getUserRank(Long userId);
}
