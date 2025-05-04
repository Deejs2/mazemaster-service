/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:2/24/2025
 * Time:4:36 PM
 */

package com.nepal.collegehub.otp.entity;

import com.nepal.collegehub.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserEntity user;

    @Column(unique = true, nullable = false)
    private String otpValue;

    private LocalDateTime expiryTime;

    private Boolean isUsed;

    @Enumerated(EnumType.STRING)
    private OTPPurpose purpose;

    @PrePersist
    public void prePersist() {
        this.isUsed = false;
    }
}
