package com.game.mazemaster_service.user_progress.entity;

import com.game.mazemaster_service.config.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProgress extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id", nullable=false)
    private Long userId;

    @Column(name="maze_id", nullable=false)
    private Long mazeId;

    @Column(nullable=false)
    private Integer bestScore;

    @Column(nullable=false)
    private Integer completionTime;

    @Column(nullable=false)
    private Integer bfsUsed;

    @Column(nullable=false)
    private Integer attempts;

    @Column(nullable=false)
    private Instant lastAttempt;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}
