package com.game.mazemaster_service.auth.service;

import com.game.mazemaster_service.auth.dto.TokenType;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {
    private final UserInfoRepository userInfoRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(!authHeader.startsWith(TokenType.Bearer.name())){
            return;
        }

        UserInfoEntity user = userInfoRepository.findByEmailId(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastLoginTime(LocalDateTime.now());
        user.setActive(false);

        userInfoRepository.save(user);
        log.info("User {} logged out successfully", authentication.getName());
    }
}
