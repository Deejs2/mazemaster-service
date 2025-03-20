package com.game.mazemaster_service.maze.service;

import com.game.mazemaster_service.maze.dto.MazeResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.repository.MazeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MazeServiceImpl implements MazeService{
    private final MazeRepository mazeRepository;
    @Override
    public List<MazeResponse> getAllMazeByCategory(LevelCategory levelCategory) {
        log.info("Fetching all mazes by category: {}", levelCategory);
        return mazeRepository.findByLevelCategory(levelCategory).stream().map(MazeResponse::new).toList();
    }

    @Override
    public List<MazeResponse> getAllMazeByCategoryAndLevel(LevelCategory levelCategory, int level) {
        log.info("Fetching all mazes by category: {} and level: {}", levelCategory, level);
        return mazeRepository.findByLevelCategoryAndLevelNumber(levelCategory, level).stream().map(MazeResponse::new).toList();
    }
}
