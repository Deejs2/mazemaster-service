package com.game.mazemaster_service.maze.service;

import com.game.mazemaster_service.maze.dto.MazeResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;

import java.util.List;

public interface MazeService {
    List<MazeResponse> getAllMazeByCategory(LevelCategory levelCategory);
    List<MazeResponse> getAllMazeByCategoryAndLevel(LevelCategory levelCategory, int level);
}
