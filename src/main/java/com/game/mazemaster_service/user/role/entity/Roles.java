/**
 * Author: Utsab Dahal
 * User:LEGION
 * Date:3/15/2025
 * Time:6:47 AM
 */

package com.nepal.collegehub.user.role.entity;

import com.nepal.collegehub.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.stream.DoubleStream;

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
