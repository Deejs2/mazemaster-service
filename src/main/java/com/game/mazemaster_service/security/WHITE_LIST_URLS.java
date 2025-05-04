package com.game.mazemaster_service.security;

import lombok.Getter;

@Getter
public enum WHITE_LIST_URLS {
    SIGN_IN("/api/v1/auth/sign-in"),
    FORGOT_PASSWORD("/api/v1/auth/forgot-password"),
    RESET_PASSWORD("/api/v1/auth/reset-password"),
    MAZE("/api/v1/maze/**"),
    V1_ALL("/v1/**"),

    HOME("/"),

    FILES("/files/**");

    private final String url;

    WHITE_LIST_URLS(String url) {
        this.url = url;
    }

}