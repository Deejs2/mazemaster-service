package com.game.mazemaster_service.user.config;

import com.game.mazemaster_service.user.entity.UserInfoEntity;
import com.game.mazemaster_service.user.repository.UserInfoRepository;
import com.game.mazemaster_service.user.role.entity.UserRole;
import com.game.mazemaster_service.user.role.repository.RolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitialUserInfo implements CommandLineRunner {
    private final UserInfoRepository userInfoRepo;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        if (userInfoRepo.count() == 0) {
            log.info("No user found in the database. Adding default user");
            UserInfoEntity manager = new UserInfoEntity();
            manager.setFullName("Admin");
            manager.setPassword(passwordEncoder.encode("Admin@123"));
            manager.setRoles(List.of(
                    rolesRepository.findByName(UserRole.ROLE_ADMINISTRATOR.toString()).orElseThrow(
                            () -> new RuntimeException("ROLE_ADMINISTRATOR Role not found")
                    )
            ));
            manager.setEmailId("admin@mazemaster.com");

            UserInfoEntity admin = new UserInfoEntity();
            admin.setFullName("Player One");
            admin.setPassword(passwordEncoder.encode("Player@123"));
            admin.setRoles(List.of(
                    rolesRepository.findByName(UserRole.ROLE_PLAYER.toString()).orElseThrow(
                            () -> new RuntimeException("ROLE_PLAYER not found")
                    )
            ));
            admin.setEmailId("playerone@mazemaster.com");

            UserInfoEntity user = new UserInfoEntity();
            user.setFullName("Player Two");
            user.setPassword(passwordEncoder.encode("Guest@123"));
            user.setRoles(List.of(
                    rolesRepository.findByName(UserRole.ROLE_GUEST.toString()).orElseThrow(
                            () -> new RuntimeException("ROLE_GUEST Role not found")
                    )
            ));
            user.setEmailId("guest@mazemaster.com");

            userInfoRepo.saveAll(List.of(manager,admin,user));
        }
    }

}
