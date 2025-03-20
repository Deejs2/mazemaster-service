package com.game.mazemaster_service.maze.dto;

import com.game.mazemaster_service.maze.entity.LevelCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MazeRequest {
    @NotBlank(message = "Level category is mandatory")
    private LevelCategory levelCategory;
    @NotEmpty(message = "Level number is mandatory")
    private int levelNumber;
    @NotBlank(message = "Maze data is mandatory")
    private String mazeData;
    @NotBlank(message = "Starting position is mandatory")
    private String startingPosition;
    @NotBlank(message = "Ending position is mandatory")
    private String endingPosition;
    @NotEmpty(message = "Base score is mandatory")
    private int baseScore;
}
