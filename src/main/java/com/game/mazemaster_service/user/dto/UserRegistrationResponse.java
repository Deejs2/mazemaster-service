package com.game.mazemaster_service.user.dto;


import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.role.dto.RolesResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationResponse {
    Long id;
    String fullName;
    String userEmail;
    RolesResponse userRole;

    public UserRegistrationResponse(UserInfoEntity user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.userEmail = user.getEmailId();
        this.userRole = user.getRoles().stream()
                .findFirst()
                .map(RolesResponse::new)
                .orElse(null);
    }
}
