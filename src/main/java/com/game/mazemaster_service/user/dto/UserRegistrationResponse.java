package com.game.mazemaster_service.user.dto;


import com.game.mazemaster_service.user.entity.UserInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationResponse {
    Long id;
    String username;
    String email;

    public UserRegistrationResponse(UserInfoEntity user) {
        this.id = user.getId();
        this.username = user.getFullName();
        this.email = user.getEmailId();
    }
}
