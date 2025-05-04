package com.nepal.collegehub.security;

import lombok.Getter;

@Getter
public enum WHITE_LIST_URLS {
    SIGN_IN("/api/v1/auth/sign-in"),
    FORGOT_PASSWORD("/api/v1/auth/forgot-password"),
    RESET_PASSWORD("/api/v1/auth/reset-password"),
    COLLEGE_REQUEST("/api/v1/college/request"),
    COLLEGE_VALIDATE("/api/v1/college/validate"),
    COLLEGE_LEVEL("/api/v1/college/level"),
    COURSE_DISCIPLINE("/api/v1/course/disciplines"),
    GET_ALL_LOCATIONS("/api/v1/location/province"),
    GET_BOARD_TYPES("/api/v1/board/types"),
    GET_SOCIAL_MEDIA_TYPES("/api/v1/socials"),
    V1_ALL("/v1/**"),
    FILES("/files/**");

    private final String url;

    WHITE_LIST_URLS(String url) {
        this.url = url;
    }

}