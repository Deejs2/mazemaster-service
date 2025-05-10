package com.game.mazemaster_service.user_progress.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProgressRequest {
    private Long mazeId;
    private Integer score;
    private Integer completionTime;
    private Integer bfsUsed;
}
