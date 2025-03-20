package com.game.mazemaster_service.maze.dto;

import com.game.mazemaster_service.maze.entity.Maze;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MazeResponse {
    private Long id;
    private String levelCategory;
    private int levelNumber;
    private List<List<Integer>> mazeData;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int baseScore;

    public MazeResponse(Maze maze) {
        this.id = maze.getId();
        this.levelCategory = maze.getLevelCategory().name();
        this.levelNumber = maze.getLevelNumber();
        this.mazeData = maze.getMazeData();
        this.startX = maze.getStartX();
        this.startY = maze.getStartY();
        this.endX = maze.getEndX();
        this.endY = maze.getEndY();
        this.baseScore = maze.getBaseScore();
    }
}
