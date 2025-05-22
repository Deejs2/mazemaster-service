package com.game.mazemaster_service.user_progress.dto;

import com.game.mazemaster_service.user_progress.entity.UserProgress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
public class UserProgressResponse {
    private Long mazeId;
    private Integer bestScore;
    private Integer completionTime;
    private Integer bfsUsed;
    private Integer attempts;
    private LocalDateTime lastAttempt;

    public UserProgressResponse(UserProgress userProgress) {
        this.mazeId = userProgress.getMazeId();
        this.bestScore = userProgress.getBestScore();
        this.completionTime = userProgress.getCompletionTime();
        this.bfsUsed = userProgress.getBfsUsed();
        this.attempts = userProgress.getAttempts();
        this.lastAttempt = userProgress.getLastAttempt().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
