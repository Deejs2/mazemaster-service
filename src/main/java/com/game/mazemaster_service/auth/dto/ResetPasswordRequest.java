/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/4/2025
 * Time:4:31 PM
 */

package com.nepal.collegehub.auth.dto.request.forgot_password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String otp;
    private String newPassword;
}
