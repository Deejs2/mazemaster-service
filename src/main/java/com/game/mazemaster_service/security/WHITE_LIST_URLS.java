package com.game.mazemaster_service.security;

import lombok.Getter;

@Getter
public enum WHITE_LIST_URLS {
    SIGN_IN("/api/v1/auth/**"),
    MAZE("/api/v1/maze/**"),
    V1_ALL("/v1/**"),
    VALIDATE_OTP("/api/v1/otp/**"),
    LEADERBOARD("/api/v1/leaderboard/**"),
    PUBLIC("/api/v1/public/**"),

    HOME("/"),

    FILES("/files/**");

    private final String url;

    WHITE_LIST_URLS(String url) {
        this.url = url;
    }

}