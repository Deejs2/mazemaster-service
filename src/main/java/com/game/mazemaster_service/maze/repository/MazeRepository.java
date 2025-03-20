package com.game.mazemaster_service.maze.repository;

import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.entity.Maze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MazeRepository extends JpaRepository<Maze, Long> {
    List<Maze> findByLevelCategory(LevelCategory levelCategory);
    List<Maze> findByLevelCategoryAndLevelNumber(LevelCategory levelCategory, int levelNumber);
}
