package com.mrthaw.inventory_backend.goods_issued.entity;

import com.mrthaw.inventory_backend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsIssued extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String goodsIssuedNo;

    private LocalDate goodsIssuedDate;

    private Long saleOrderId;

    private String referenceFormNo;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Builder.Default
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "goodsIssued")
    private List<GoodsIssuedItem> goodsIssuedItems = new ArrayList<>();

    private BigDecimal totalQty;

    private BigDecimal totalAmount;

    public void addAllItem(List<GoodsIssuedItem> itemList) {
        itemList.forEach(this::addItem);

        recalculateQty();
    }

    public void addItem(GoodsIssuedItem item) {
        item.setGoodsIssued(this);

        this.goodsIssuedItems.add(item);
    }

    public void updateAllItem(List<GoodsIssuedItem> itemList) {
        this.goodsIssuedItems.clear();

        addAllItem(itemList);
    }

    public List<GoodsIssuedItem> getAllItems() {
        return Collections.unmodifiableList(goodsIssuedItems);
    }

    public void recalculateQty() {
        this.totalQty = this.goodsIssuedItems.stream()
                .map(GoodsIssuedItem::getQty)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        this.totalAmount = this.goodsIssuedItems.stream()
                .map(GoodsIssuedItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
