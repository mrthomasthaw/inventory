package com.mrthaw.inventory_backend.goods_issued.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.common.entity.BaseEntity;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoodsIssuedItem extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private GoodsIssued goodsIssued;

    private BigDecimal qty;

    private BigDecimal totalAmount;

}
