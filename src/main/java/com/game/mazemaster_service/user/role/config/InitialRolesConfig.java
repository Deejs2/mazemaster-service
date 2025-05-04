///**
// * Author: Utsab Dahal
// * User:LEGION
// * Date:3/15/2025
// * Time:7:24 AM
// */
//
//package com.nepal.collegehub.user.role.config;
//
//import com.nepal.collegehub.user.role.entity.Roles;
//import com.nepal.collegehub.user.role.entity.UserRole;
//import com.nepal.collegehub.user.role.repository.RolesRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@Order(1)
//public class InitialRolesConfig implements CommandLineRunner {
//
//    private final RolesRepository rolesRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (rolesRepository.count() < 3) {
//            log.info("Roles creation request received");
//            createRoleIfNotExists(UserRole.SUPER_ADMIN.toString(), "Super Admin Role");
//            createRoleIfNotExists(UserRole.COLLEGE_ADMIN.toString(), "Admin Role");
//            createRoleIfNotExists(UserRole.COLLEGE_USER.toString(), "User Role");
//            log.info("Roles created");
//        } else {
//            log.info("Roles already exist");
//        }
//    }
//
//    private void createRoleIfNotExists(String name, String description) {
//        if (rolesRepository.findByName(name).isEmpty()) {
//            Roles role = Roles.builder()
//                    .name(name)
//                    .description(description)
//                    .build();
//            rolesRepository.save(role);
//            log.info("Role {} inserted", name);
//        }
//    }
//}