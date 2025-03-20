package com.game.mazemaster_service.maze.config;

import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.entity.Maze;
import com.game.mazemaster_service.maze.repository.MazeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@RequiredArgsConstructor
public class InsertMazeData {
    private final MazeRepository mazeRepository;
    private static final int LEVELS_PER_CATEGORY = 5;
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @PostConstruct
    public void init() {
        if (mazeRepository.count() == 0) {
            insertAllMazes();
        }
    }

    private void insertAllMazes() {
        List<Maze> allMazes = new ArrayList<>();

        // Generate 5 levels for each category
        for (int level = 1; level <= LEVELS_PER_CATEGORY; level++) {
            allMazes.addAll(generateMazesForCategory(LevelCategory.EASY, level));
            allMazes.addAll(generateMazesForCategory(LevelCategory.MEDIUM, level));
            allMazes.addAll(generateMazesForCategory(LevelCategory.HARD, level));
        }

        mazeRepository.saveAll(allMazes);
    }

    private List<Maze> generateMazesForCategory(LevelCategory category, int level) {
        List<Maze> mazes = new ArrayList<>();
        int baseScore = getBaseScore(category, level);
        int mazeSize = getMazeSize(category, level);

        List<List<Integer>> mazeData = generateMazeData(mazeSize);
        int[][] positions = generateValidPositions(mazeData, mazeSize);

        mazes.add(Maze.builder()
                .levelCategory(category)
                .levelNumber(level)
                .mazeData(mazeData)
                .startX(positions[0][0])
                .startY(positions[0][1])
                .endX(positions[1][0])
                .endY(positions[1][1])
                .baseScore(baseScore)
                .build());

        return mazes;
    }

    private int getBaseScore(LevelCategory category, int level) {
        return switch (category) {
            case EASY -> 100 + (level - 1) * 10;
            case MEDIUM -> 200 + (level - 1) * 10;
            case HARD -> 300 + (level - 1) * 10;
        };
    }

    private int getMazeSize(LevelCategory category, int level) {
        return switch (category) {
            case EASY -> 5 + (level - 1) * 2;  // Starts at 5x5, increases by 2 per level
            case MEDIUM -> 8 + (level - 1) * 2; // Starts at 8x8
            case HARD -> 10 + (level - 1) * 2;  // Starts at 10x10
        };
    }

    private List<List<Integer>> generateMazeData(int size) {
        List<List<Integer>> maze = new ArrayList<>();
        double wallProbability = getWallProbability(size);

        // Generate random maze with open borders
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                if (i == 0 || i == size - 1 || j == 0 || j == size - 1) {
                    row.add(1); // Border walls
                } else {
                    row.add(random.nextDouble() < wallProbability ? 1 : 0);
                }
            }
            maze.add(row);
        }
        return maze;
    }

    private double getWallProbability(int size) {
        return switch ((int) Math.sqrt(size)) {
            case 5 -> 0.2;  // Easy
            case 8 -> 0.3;  // Medium
            case 10 -> 0.4; // Hard
            default -> 0.3; // Default
        };
    }

    private int[][] generateValidPositions(List<List<Integer>> maze, int size) {
        int[][] positions = new int[2][2];

        // Start position (always in top-left quadrant)
        do {
            positions[0][0] = random.nextInt(1, size/2);
            positions[0][1] = random.nextInt(1, size/2);
        } while (maze.get(positions[0][0]).get(positions[0][1]) == 1);

        // End position (always in bottom-right quadrant)
        do {
            positions[1][0] = random.nextInt(size/2, size - 1);
            positions[1][1] = random.nextInt(size/2, size - 1);
        } while (maze.get(positions[1][0]).get(positions[1][1]) == 1 ||
                (positions[1][0] == positions[0][0] && positions[1][1] == positions[0][1]));

        // Ensure positions are open
        maze.get(positions[0][0]).set(positions[0][1], 0);
        maze.get(positions[1][0]).set(positions[1][1], 0);

        return positions;
    }
}