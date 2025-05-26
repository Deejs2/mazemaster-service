package com.game.mazemaster_service.leaderboard.service;

import com.game.mazemaster_service.leaderboard.dto.LeaderboardResponse;
import com.game.mazemaster_service.leaderboard.dto.MazeResponse;
import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.maze.repository.MazeRepository;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.messages.UserExceptionMessages;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import com.game.mazemaster_service.user_progress.entity.UserProgress;
import com.game.mazemaster_service.user_progress.repository.UserProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderboardServiceImpl implements LeaderboardService {
    private final UserProgressRepository userProgressRepository;
    private final UserInfoRepository userInfoRepository;
    private final MazeRepository mazeRepository;

    private LeaderboardResponse mapToLeaderboardResponse(UserProgress userProgress) {
        MazeResponse mazeResponse = mazeRepository.findById(userProgress.getMazeId())
                .map(MazeResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Maze not found with ID: " + userProgress.getMazeId()));
        return new LeaderboardResponse(userProgress, mazeResponse);
    }

    @Override
    public Page<LeaderboardResponse> getGlobalLeaderboard(Pageable pageable) {
        log.info("Fetching global leaderboard");

        Page<UserProgress> progressPage = userProgressRepository.getGlobalLeaderboard(pageable);
        List<LeaderboardResponse> responseList = progressPage.getContent()
                .stream()
                .map(this::mapToLeaderboardResponse)
                .toList();

        return new PageImpl<>(responseList, pageable, progressPage.getTotalElements());
    }

    @Override
    public Page<LeaderboardResponse> getGlobalLeaderboard(LevelCategory category, Pageable pageable) {
        log.info("Fetching leaderboard for category: {}", category);

        Page<UserProgress> progressPage = userProgressRepository.getCategoryLeaderboard(category, pageable);
        List<LeaderboardResponse> responseList = progressPage.getContent()
                .stream()
                .map(this::mapToLeaderboardResponse)
                .toList();

        return new PageImpl<>(responseList, pageable, progressPage.getTotalElements());
    }
    @Override
    public Integer getUserRank(Long userId) {
        log.info("Fetching rank for user: {}", userId);
        UserInfoEntity user = userInfoRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserExceptionMessages.USER_NOT_FOUND));
        return userProgressRepository.getUserRank(user.getId());
    }

}