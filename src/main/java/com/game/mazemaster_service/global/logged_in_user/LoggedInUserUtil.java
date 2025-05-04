package com.nepal.collegehub.utils.logged_in_user;

import com.nepal.collegehub.college.college.entity.College;
import com.nepal.collegehub.college.college.repository.CollegeRepository;
import com.nepal.collegehub.user.entity.UserEntity;
import com.nepal.collegehub.user.repository.UserInfoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class LoggedInUserUtil {

    private final UserInfoRepository userInfoRepository;
    private final CollegeRepository collegeRepository;

    public UserEntity getLoggedInUser() {
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

    public College getLoggedInCollege() {
        UserEntity user = getLoggedInUser();
        return collegeRepository.findByCollegeUsersContaining(user)
                .orElseThrow(() -> new EntityNotFoundException("No college found for user: " + user.getEmailId()));
    }
}