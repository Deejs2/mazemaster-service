/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/4/2025
 * Time:12:46 PM
 */

package com.nepal.collegehub.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    private String message;
    private String email;
    private LocalDateTime expiresAt;

}
