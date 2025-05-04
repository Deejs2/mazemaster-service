package com.game.mazemaster_service.user.dto.response;


import com.nepal.collegehub.user.entity.UserEntity;
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

    public UserRegistrationResponse(UserEntity user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.userEmail = user.getEmailId();
        this.userRole = user.getRoles().stream()
                .findFirst()
                .map(RolesResponse::new)
                .orElse(null);
    }
}
