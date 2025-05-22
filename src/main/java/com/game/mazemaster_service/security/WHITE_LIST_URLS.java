package com.game.mazemaster_service.security;

import lombok.Getter;

@Getter
public enum WHITE_LIST_URLS {
    SIGN_IN("/api/v1/auth/sign-in"),
    REGISTER("/api/v1/auth/register"),
    FORGOT_PASSWORD("/api/v1/auth/forgot-password"),
    RESET_PASSWORD("/api/v1/auth/reset-password"),
    MAZE("/api/v1/maze"),
    MAZE_LEVEL("/api/v1/maze/level"),
    V1_ALL("/v1/**"),
    VALIDATE_OTP("/api/v1/otp/**"),
    LEADERBOARD("/api/v1/leaderboard/**"),
    GET_USER_BY_ID("/api/v1/user/**"),

    HOME("/"),

    FILES("/files/**");

    private final String url;

    WHITE_LIST_URLS(String url) {
        this.url = url;
    }

}