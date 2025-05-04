/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/4/2025
 * Time:5:08 PM
 */

package com.game.mazemaster_service.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
}
