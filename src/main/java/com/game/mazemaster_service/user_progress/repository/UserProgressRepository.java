package com.game.mazemaster_service.user_progress.repository;

import com.game.mazemaster_service.user_progress.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByUserIdAndMazeId(Long userId, Long mazeId);
    List<UserProgress> findAllByUserId(Long userId);
    List<UserProgress> findAllByMazeId(Long mazeId);
}
