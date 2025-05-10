package com.game.mazemaster_service.maze.config;

import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.entity.Maze;
import com.game.mazemaster_service.maze.repository.MazeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
        for (int i = 0; i < size; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(1); // Start with all walls
            }
            maze.add(row);
        }

        int[][] positions = generateValidPositions(maze, size);
        int startX = positions[0][0];
        int startY = positions[0][1];
        int endX = positions[1][0];
        int endY = positions[1][1];

        List<int[]> path = createPathWithBFS(maze, startX, startY, endX, endY);
        addRandomWalls(maze, size, path);
        return maze;
    }

    private List<int[]> createPathWithBFS(List<List<Integer>> maze, int startX, int startY, int endX, int endY) {
        int size = maze.size();
        boolean[][] visited = new boolean[size][size];
        int[][] parent = new int[size * size][2];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;
        parent[startX * size + startY] = new int[]{-1, -1};

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            if (x == endX && y == endY) {
                break;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];
                if (isValidMove(maze, newX, newY, visited)) {
                    queue.add(new int[]{newX, newY});
                    visited[newX][newY] = true;
                    parent[newX * size + newY] = new int[]{x, y};
                }
            }
        }

        // Reconstruct path from end to start
        List<int[]> path = new LinkedList<>();
        int[] current = new int[]{endX, endY};
        while (current[0] != -1) {
            path.add(0, current);
            current = parent[current[0] * size + current[1]];
        }

        // Mark the path in the maze
        for (int[] cell : path) {
            maze.get(cell[0]).set(cell[1], 0);
        }

        return path;
    }


    private boolean isValidMove(List<List<Integer>> maze, int x, int y, boolean[][] visited) {
        return x >= 0 && y >= 0 && x < maze.size() && y < maze.size() && !visited[x][y];
    }

    private void addRandomWalls(List<List<Integer>> maze, int size, List<int[]> path) {
        double wallProbability = getWallProbability(size);
        boolean[][] isPathCell = new boolean[size][size];

        for (int[] cell : path) {
            isPathCell[cell[0]][cell[1]] = true;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!isPathCell[i][j] && Math.random() < wallProbability) {
                    maze.get(i).set(j, 1); // Add wall
                } else if (!isPathCell[i][j]) {
                    maze.get(i).set(j, 0); // Ensure open cell if not a path and not wall
                }
            }
        }
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
        positions[0][0] = random.nextInt(1, size / 2);
        positions[0][1] = random.nextInt(1, size / 2);
        maze.get(positions[0][0]).set(positions[0][1], 0); // Ensure it's open

        // End position (always in bottom-right quadrant)
        do {
            positions[1][0] = random.nextInt(size / 2, size - 1);
            positions[1][1] = random.nextInt(size / 2, size - 1);
        } while (positions[1][0] == positions[0][0] && positions[1][1] == positions[0][1]);

        maze.get(positions[1][0]).set(positions[1][1], 0); // Ensure it's open

        return positions;
    }
}