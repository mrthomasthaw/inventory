package com.mrthaw.inventory_backend.sale_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class SaleOrderItem extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne(optional = false)
    private SaleOrder saleOrder;

    @DecimalMin(value = "0.01")
    private BigDecimal qty;

    @DecimalMin(value = "1")
    private BigDecimal price;

    @DecimalMin(value = "0.01")
    private BigDecimal totalAmount;

}