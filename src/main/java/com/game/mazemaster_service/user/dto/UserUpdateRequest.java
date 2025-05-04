/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:2/27/2025
 * Time:4:27 PM
 */

package com.game.mazemaster_service.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String fullName;
}
