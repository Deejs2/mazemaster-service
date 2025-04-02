package com.game.mazemaster_service.auth.controller;

import com.game.mazemaster_service.auth.service.AuthService;
import com.game.mazemaster_service.global.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController extends BaseController {
    private final AuthService authService;
    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(Authentication authentication){

        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(authentication));
    }
}
