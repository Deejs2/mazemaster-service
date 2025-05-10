package com.game.mazemaster_service.user_progress.dto;

import com.game.mazemaster_service.user_progress.entity.UserProgress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProgressResponse {
    private Long userId;
    private Long mazeId;
    private Integer score;
    private Integer completionTime;
    private Integer bfsUsed;

    public UserProgressResponse(UserProgress userProgress) {
        this.userId = userProgress.getUserId();
        this.mazeId = userProgress.getMazeId();
        this.score = userProgress.getBestScore();
        this.completionTime = userProgress.getCompletionTime();
        this.bfsUsed = userProgress.getBfsUsed();
    }
}
