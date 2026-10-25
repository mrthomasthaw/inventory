package com.mrthaw.inventory_backend.master_data.main_category.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MainCategory extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private Integer status;

    public MainCategory(Long id) {
        this.id = id;
    }
}
