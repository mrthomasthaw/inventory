package com.mrthaw.inventory_backend.master_data.user_role.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Integer status;

    public UserRole(Long id) {
        this.id = id;
    }
}
