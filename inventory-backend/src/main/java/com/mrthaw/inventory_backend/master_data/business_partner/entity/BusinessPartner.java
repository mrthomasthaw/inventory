package com.mrthaw.inventory_backend.master_data.business_partner.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessPartner extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String address;

    private Boolean isCustomer;

    private Integer status;

    public BusinessPartner(Long id) {
        this.id = id;
    }
}
