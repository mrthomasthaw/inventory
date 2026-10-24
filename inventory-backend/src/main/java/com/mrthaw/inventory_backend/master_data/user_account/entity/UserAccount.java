package com.mrthaw.inventory_backend.master_data.user_account.entity;


import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String profileName;

    private String password;

    @ManyToOne
    private UserRole userRole;

    private Integer status;
}
