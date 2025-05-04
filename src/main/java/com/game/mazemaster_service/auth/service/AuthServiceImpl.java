package com.game.mazemaster_service.auth.service;

import com.game.mazemaster_service.auth.dto.*;
import com.game.mazemaster_service.auth.entity.RefreshTokenEntity;
import com.game.mazemaster_service.auth.message.AuthExceptionMessages;
import com.game.mazemaster_service.auth.message.AuthLogMessages;
import com.game.mazemaster_service.auth.message.AuthResponseMessages;
import com.game.mazemaster_service.auth.repository.RefreshTokenRepository;
import com.game.mazemaster_service.mail.MailService;
import com.game.mazemaster_service.otp.entity.OTP;
import com.game.mazemaster_service.otp.entity.OTPPurpose;
import com.game.mazemaster_service.otp.service.OTPService;
import com.game.mazemaster_service.security.jwt_auth.JwtTokenGenerator;
import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import com.game.mazemaster_service.user.role.dto.RolesResponse;
import com.game.mazemaster_service.user.role.entity.Roles;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserInfoRepository userInfoRepository;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepo;
    private final AuthenticationManager authenticationManager;
    private final OTPService otpService;
    private final MailService mailService;

    @Value("${frontend.domain}")
    private String frontEndUrl;
    @Value("${frontend.forgot_password}")
    private String forgotPasswordUrl;

    @Override
    public AuthResponse getJwtTokensAfterAuthentication(AuthRequest authenticationRequest, HttpServletResponse response) {
        try {
            // Authenticate the user credentials
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.email(),
                            authenticationRequest.password()
                    )
            );

            // Fetch user information after successful authentication
            var userInfoEntity = userInfoRepository.findByEmailId(authenticationRequest.email())
                    .orElseThrow(() -> {
                        log.error(AuthLogMessages.USER_NOT_FOUND, authenticationRequest.email());
                        return new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND");                    });

            // Generate JWT tokens
            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            // Save refresh token and create cookie
            saveUserRefreshToken(userInfoEntity, refreshToken);
            createRefreshTokenCookie(response, refreshToken);

            log.info(AuthLogMessages.ACCESS_TOKEN_GENERATED, userInfoEntity.getEmailId());

            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userRole(userInfoEntity.getRoles().stream().map(RolesResponse::new).toList())
                    .tokenType(TokenType.Bearer)
                    .build();

        } catch (AuthenticationException e) {
            log.error(AuthLogMessages.INVALID_CREDENTIALS, authenticationRequest.email());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AuthExceptionMessages.INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error(AuthLogMessages.EXCEPTION_AUTHENTICATING, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, AuthExceptionMessages.TRY_AGAIN);
        }
    }


    private void saveUserRefreshToken(UserInfoEntity userEntity, String refreshToken) {
        var refreshTokenEntity = RefreshTokenEntity.builder()
                .user(userEntity)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();
        refreshTokenRepo.save(refreshTokenEntity);
    }

    private void createRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
    }

    @Override
    public AuthResponse getAccessTokenUsingRefreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AuthExceptionMessages.REFRESH_TOKEN_MISSING);
        }

        // Validate refresh token from database
        var refreshTokenEntity = refreshTokenRepo.findByRefreshToken(refreshToken)
                .filter(tokens -> !tokens.isRevoked())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, AuthExceptionMessages.REFRESH_TOKEN_REVOKED));

        UserInfoEntity userEntity = refreshTokenEntity.getUser();

        // Generate new access token
        Authentication authentication = createAuthenticationObject(userEntity);
        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        log.info(AuthLogMessages.ACCESS_TOKEN_GENERATED, userEntity.getEmailId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiry(5 * 60)
                .tokenType(TokenType.Bearer)
                .build();
    }

    private static Authentication createAuthenticationObject(UserInfoEntity userEntity) {
        // Extract user details from UserDetailsEntity
        String username = userEntity.getEmailId();
        String password = userEntity.getPassword();
        List<Roles> roles = userEntity.getRoles();

        // Extract authorities from roles
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) role::getName)
                .toList();

        return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }


    @Override
    public ForgotPasswordResponse forgotPassword(String email) {
        UserInfoEntity userEntity = userInfoRepository.findByEmailId(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AuthExceptionMessages.USER_NOT_FOUND + email));
        OTP otp = otpService.saveOTP(userEntity, OTPPurpose.FORGOT_PASSWORD);
        String forgotPasswordLink = frontEndUrl + forgotPasswordUrl + "?token=" + otp.getOtpValue();
        mailService.sendForgotPasswordMail(userEntity, forgotPasswordLink, otp.getExpiryTime());
        return new ForgotPasswordResponse(AuthResponseMessages.PASSWORD_RESET_LINK_SENT, userEntity.getEmailId(), otp.getExpiryTime());
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserInfoEntity user = otpService.getOTP(resetPasswordRequest.getOtp(), OTPPurpose.FORGOT_PASSWORD).getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(resetPasswordRequest.getNewPassword()));
        userInfoRepository.save(user);
        return AuthResponseMessages.PASSWORD_RESET_SUCCESS;
    }
}
