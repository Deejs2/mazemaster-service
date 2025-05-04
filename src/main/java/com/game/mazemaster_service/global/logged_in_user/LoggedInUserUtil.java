package com.game.mazemaster_service.global.logged_in_user;

import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedInUserUtil {

    private final UserInfoRepository userInfoRepository;

    public UserInfoEntity getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = getUsername(authentication);

            return userInfoRepository.findByEmailId(username)
                    .orElseThrow(() -> new EntityNotFoundException("No user found with username: " + username));
        }
        throw new IllegalStateException("No authenticated user found in SecurityContext");
    }

    private String getUsername(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username;

        switch (principal) {
            case UserDetails userDetails -> username = userDetails.getUsername(); // Extract username from UserDetails
            case String user -> username = user;
            default -> throw new IllegalStateException("Authentication principal is not of expected type: " + principal.getClass());
        }
        return username;
    }
}