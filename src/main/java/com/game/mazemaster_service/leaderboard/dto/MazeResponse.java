package com.game.mazemaster_service.leaderboard.dto;

import com.game.mazemaster_service.maze.entity.Maze;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MazeResponse {
    private String levelCategory;
    private int levelNumber;

    public MazeResponse(Maze maze) {
        this.levelCategory = maze.getLevelCategory().name();
        this.levelNumber = maze.getLevelNumber();
    }
}
