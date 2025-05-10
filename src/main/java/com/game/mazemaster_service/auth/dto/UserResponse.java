package com.game.mazemaster_service.auth.dto;

import com.game.mazemaster_service.user.entity.UserInfoEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;

    private LocalDateTime lastLogin;

    public UserResponse (UserInfoEntity userInfoEntity) {
        this.id = userInfoEntity.getId();
        this.username = userInfoEntity.getFullName();
        this.email = userInfoEntity.getEmailId();
        this.lastLogin = userInfoEntity.getLastLoginTime();
    }
}
