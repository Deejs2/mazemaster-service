package com.game.mazemaster_service.user_progress.repository;

import com.game.mazemaster_service.maze.entity.LevelCategory;
import com.game.mazemaster_service.user_progress.entity.UserProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    Optional<UserProgress> findByUserIdAndMazeId(Long userId, Long mazeId);
    List<UserProgress> findAllByUserId(Long userId);
    List<UserProgress> findAllByMazeId(Long mazeId);
    @Query("SELECT up FROM UserProgress up ORDER BY up.bestScore DESC")
    Page<UserProgress> getGlobalLeaderboard(Pageable pageable);

    @Query("""
    SELECT up FROM UserProgress up 
    WHERE up.mazeId IN (
        SELECT m.id FROM Maze m WHERE m.levelCategory = :category
    )
    ORDER BY up.bestScore DESC
""")
    Page<UserProgress> getCategoryLeaderboard(@Param("category") LevelCategory category, Pageable pageable);


    @Query(value = """
    SELECT COUNT(*) + 1 
    FROM UserProgress u 
    WHERE u.bestScore > (
        SELECT MAX(up.bestScore) 
        FROM UserProgress up 
        WHERE up.userId = :userId
    )
""")
    Integer getUserRank(@Param("userId") Long userId);




}
