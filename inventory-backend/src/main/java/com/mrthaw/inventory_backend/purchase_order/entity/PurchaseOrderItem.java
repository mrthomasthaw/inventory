package com.mrthaw.inventory_backend.purchase_order.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Item item;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @Column(nullable = false)
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Column(nullable = false)
    @DecimalMin(value = "0.01")
    private BigDecimal qty;

    @Column(nullable = false)
    @DecimalMin(value = "0.01")
    private BigDecimal totalAmount;

}
