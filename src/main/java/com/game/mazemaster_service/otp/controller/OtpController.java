package com.game.mazemaster_service.otp.controller;

import com.game.mazemaster_service.global.BaseController;
import com.game.mazemaster_service.global.dto.ApiResponse;
import com.game.mazemaster_service.otp.dto.OtpValidateRequest;
import com.game.mazemaster_service.otp.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/otp")
public class OtpController extends BaseController {
    private final OTPService otpService;

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Void>> validateOtp(@RequestBody OtpValidateRequest otpValidateRequest) {
        return successResponse(otpService.validateOTP(otpValidateRequest));
    }

    @PostMapping("/resend")
    public ResponseEntity<ApiResponse<Void>> resendOtp(@RequestBody String email){
        return successResponse(otpService.resendOtp(email));
    }
}
