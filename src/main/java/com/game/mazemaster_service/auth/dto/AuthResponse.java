package com.game.mazemaster_service.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.mazemaster_service.user.role.dto.RolesResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_expiry")
    private int accessTokenExpiry;

    @JsonProperty("token_type")
    private TokenType tokenType;

    @JsonProperty("user_role")
    private List<RolesResponse> userRole;

}