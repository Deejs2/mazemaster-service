package com.game.mazemaster_service.user_progress.service;

import com.game.mazemaster_service.global.logged_in_user.LoggedInUserUtil;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.entity.Maze;
import com.game.mazemaster_service.maze.repository.MazeRepository;
import com.game.mazemaster_service.user_progress.dto.HighestLevelResponse;
import com.game.mazemaster_service.user_progress.dto.UserProgressRequest;
import com.game.mazemaster_service.user_progress.dto.UserProgressResponse;
import com.game.mazemaster_service.user_progress.entity.UserProgress;
import com.game.mazemaster_service.user_progress.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService{
    private final UserProgressRepository userProgressRepository;
    private final LoggedInUserUtil loggedInUserUtil;
    private final MazeRepository mazeRepository;
    @Override
    public UserProgressResponse saveUserProgress(UserProgressRequest userProgressRequest) {
        log.info("Saving user progress for user: {}, maze: {}",
                loggedInUserUtil.getLoggedInUser(), userProgressRequest.getMazeId());
        var userId = loggedInUserUtil.getLoggedInUser().getId();
        var mazeId = userProgressRequest.getMazeId();
        var score = userProgressRequest.getScore();
        var time = userProgressRequest.getCompletionTime();
        var bfsUsed = userProgressRequest.getBfsUsed();
        var userProgress = saveOrUpdate(userId, mazeId, score, time, bfsUsed);
        return new UserProgressResponse(userProgress);
    }

    public UserProgress saveOrUpdate(Long userId, Long mazeId,
                                     int score, int time, int bfsUsed) {
        var now = Instant.now();
        UserProgress userProgress = userProgressRepository.findByUserIdAndMazeId(userId, mazeId)
                .map(existing -> {
                    existing.setAttempts(existing.getAttempts() + 1);
                    existing.setLastAttempt(now);
                    if (score > existing.getBestScore()) {
                        existing.setBestScore(score);
                        existing.setCompletionTime(time);
                        existing.setBfsUsed(bfsUsed);
                    }
                    return existing;
                })
                .orElseGet(() -> UserProgress.builder()
                        .userId(userId)
                        .mazeId(mazeId)
                        .bestScore(score)
                        .completionTime(time)
                        .bfsUsed(bfsUsed)
                        .attempts(1)
                        .lastAttempt(now)
                        .build()
                );
        return userProgressRepository.save(userProgress);
    }

    @Override
    public UserProgressResponse getUserProgress(Long userId, Long mazeId) {
        log.info("Getting user progress for user: {}, maze: {}",
                loggedInUserUtil.getLoggedInUser(), mazeId);
        return userProgressRepository.findByUserIdAndMazeId(userId, mazeId)
                .map(UserProgressResponse::new)
                .orElseThrow(
                        () -> new RuntimeException("User progress not found for user: " + userId + ", maze: " + mazeId)
                );
    }

    @Override
    public List<UserProgressResponse> getAllUserProgressByMazeId(Long mazeId) {
        log.info("Getting all user progress for maze: {}",
                loggedInUserUtil.getLoggedInUser());
        return userProgressRepository.findAllByMazeId(mazeId).stream()
                .map(UserProgressResponse::new)
                .toList();
    }

    @Override
    public List<UserProgressResponse> getAllUserProgressByUserId(Long userId) {
        log.info("Getting all user progress for user: {}",
                loggedInUserUtil.getLoggedInUser());
        return userProgressRepository.findAllByUserId(userId).stream()
                .map(UserProgressResponse::new)
                .toList();
    }

    @Override
    public void deleteUserProgress(Long userId, Long mazeId) {
        log.info("Deleting user progress for user: {}, maze: {}",
                loggedInUserUtil.getLoggedInUser(), mazeId);
        var userProgress = userProgressRepository.findByUserIdAndMazeId(userId, mazeId)
                .orElseThrow(
                        () -> new RuntimeException("User progress not found for user: " + userId + ", maze: " + mazeId)
                );
        userProgressRepository.delete(userProgress);
        log.info("Deleted user progress for user: {}, maze: {}",
                loggedInUserUtil.getLoggedInUser(), mazeId);
    }

    @Override
    public HighestLevelResponse getHighestLevelByCategory(LevelCategory category) {
        log.info("Getting highest level for category: {}",
                loggedInUserUtil.getLoggedInUser());
        var userId = loggedInUserUtil.getLoggedInUser().getId();

        // Fetch all user progress for the logged-in user
        var userProgressList = userProgressRepository.findAllByUserId(userId);

        // Filter progress entries by the given category
        var mazeIds = userProgressList.stream()
                .map(UserProgress::getMazeId)
                .toList();

        // Fetch all mazes for the given maze IDs and filter by category
        var highestLevel = mazeRepository.findAllById(mazeIds).stream()
                .filter(maze -> maze.getLevelCategory() == category)
                .mapToInt(Maze::getLevelNumber)
                .max()
                .orElse(0); // Default to 0 if no levels are found

        // Return the response
        return new HighestLevelResponse(category.name(), highestLevel);
    }
}
