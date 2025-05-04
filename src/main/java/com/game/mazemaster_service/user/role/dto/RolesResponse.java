package com.game.mazemaster_service.user.role.dto;

import com.game.mazemaster_service.user.role.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolesResponse {
    private String role;
    private String description;

    public RolesResponse(Roles role) {
        this.role = role.getName();
        this.description = role.getDescription();
    }
}
