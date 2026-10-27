package com.mrthaw.inventory_backend.master_data.item.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.main_category.entity.MainCategory;
import com.mrthaw.inventory_backend.master_data.unit.entity.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    @Column(unique = true)
    private String itemCode;

    private String description;

    private Integer status;

    @ManyToOne
    private MainCategory mainCategory;

    @ManyToOne
    private Unit unit;

    public Item(Long id) {
        this.id = id;
    }
}
