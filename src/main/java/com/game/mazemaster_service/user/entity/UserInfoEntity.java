package com.game.mazemaster_service.user.entity;

import com.game.mazemaster_service.auth.entity.RefreshTokenEntity;
import com.game.mazemaster_service.user.role.entity.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER_INFO")
public class UserInfoEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME")
    private String fullName;

    @Column(nullable = false, name = "EMAIL_ID", unique = true)
    private String emailId;

    @Column(nullable = false, name = "PASSWORD")
    private String password;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens;

    @Column(nullable = false, name = "IS_VERIFIED")
    private boolean isVerified = false;

    @Column(nullable = false, name = "IS_ACTIVE")
    private boolean isActive = true;
}
