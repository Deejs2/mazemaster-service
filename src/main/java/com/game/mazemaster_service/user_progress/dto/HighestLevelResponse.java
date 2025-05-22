package com.game.mazemaster_service.user_progress.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HighestLevelResponse {
    private String category;
    private int highestLevel;

    public HighestLevelResponse(String category, int highestLevel) {
        this.category = category;
        this.highestLevel = highestLevel;
    }
}
