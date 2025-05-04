package com.nepal.collegehub.user.role.repository;

import com.nepal.collegehub.user.role.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);
}
