package com.game.mazemaster_service.auth.entity;

import com.game.mazemaster_service.config.auditing.Auditable;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="REFRESH_TOKENS")
public class RefreshTokenEntity extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "REFRESH_TOKEN", nullable = false, length = 10000)
    private String refreshToken;

    @Column(name = "REVOKED")
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfoEntity user;

}
