package com.game.mazemaster_service.user.role.entity;

import com.game.mazemaster_service.config.auditing.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@Table(name="roles")
public class Roles extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true, nullable=false)
    private String name;

    private String description;
}
