package com.mrthaw.inventory_backend.goods_received;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class GoodsReceived extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goodsReceivedNo;

    private LocalDate goodsReceivedDate;

    @NotNull
    private Long purchaseOrderId;

    @Version
    private Long version;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GoodsReceivedItem> goodsReceivedItems = new ArrayList<>();

    public void addItem(GoodsReceivedItemRequestDto itemDto) {

        GoodsReceivedItem item = GoodsReceivedItem.builder()
                .item(new Item(itemDto.getItemId()))
                .price(itemDto.getPrice())
                .qty(itemDto.getQty())
                .totalAmount(itemDto.getPrice().multiply(itemDto.getQty()))
                .purchaseOrder(this)
                .build();

        goodsReceivedItems.add(item);

        recalculateTotal();
    }

    public void recalculateTotal() {
        this.totalQty = this.purchaseOrderItems.stream()
                .map(PurchaseOrderItem::getQty)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.totalAmount = this.purchaseOrderItems.stream()
                .map(PurchaseOrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public void addAllItems(List<PurchaseOrderItemCreateRequestDto> itemList) {
        itemList.forEach(this::addItem);
    }

    public void updateAllItems(List<PurchaseOrderItemCreateRequestDto> itemList) {
        purchaseOrderItems.clear();

        addAllItems(itemList);
    }

    public List<PurchaseOrderItem> getItems() {
        return Collections.unmodifiableList(purchaseOrderItems);
    }
}
