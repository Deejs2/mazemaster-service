package com.game.mazemaster_service.auth;

import com.game.mazemaster_service.auth.service.AuthService;
import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController extends BaseController {

    private final AuthService authService;
    @PostMapping("/sign-in")
    public ResponseEntity<SuccessResponse> authenticateUser(Authentication authentication){
        return successResponse(authService.getJwtTokensAfterAuthentication(authentication), "User Authenticated Successfully");
    }
}