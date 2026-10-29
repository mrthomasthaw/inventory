package com.mrthaw.inventory_backend.goods_received;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class GoodsReceivedItem extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goodsReceivedItemId;

    @ManyToOne
    private Item item;

    @ManyToOne
    private GoodsReceived goodsReceived;

    @DecimalMin(value = "0.01")
    private BigDecimal qty;
}
