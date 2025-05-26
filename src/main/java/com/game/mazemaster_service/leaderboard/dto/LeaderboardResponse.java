package com.game.mazemaster_service.leaderboard.dto;

import com.game.mazemaster_service.user_progress.entity.UserProgress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaderboardResponse {
    private Long userId;
    private int totalScore;
    private int totalTime;
    private MazeResponse levelsCompleted;

    public LeaderboardResponse(UserProgress userProgress, MazeResponse mazeResponse) {
        this.userId = userProgress.getUserId();
        this.totalScore = userProgress.getBestScore();
        this.totalTime = userProgress.getCompletionTime();
        this.levelsCompleted = mazeResponse;
    }


}
