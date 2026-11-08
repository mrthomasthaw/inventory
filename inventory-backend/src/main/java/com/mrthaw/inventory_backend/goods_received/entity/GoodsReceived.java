package com.mrthaw.inventory_backend.goods_received.entity;

import com.mrthaw.inventory_backend.common.entity.AuditingEntity;
import com.mrthaw.inventory_backend.goods_received.dto.GoodsReceivedItemRequestDto;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Long purchaseOrderId;

    private Integer formStatus;

    @Version
    private Long version;

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "goodsReceived")
    private List<GoodsReceivedItem> goodsReceivedItems = new ArrayList<>(); // Note this initialization will be skipped if you don't put @Builder.Default resulting in null

    public void addItem(GoodsReceivedItemRequestDto itemDto) {

        GoodsReceivedItem item = GoodsReceivedItem.builder()
                .item(new Item(itemDto.getItemId()))
                .qty(itemDto.getQty())
                .goodsReceived(this)
                .build();

        goodsReceivedItems.add(item);
    }


    public void addAllItems(List<GoodsReceivedItemRequestDto> itemList) {
        itemList.forEach(this::addItem);
    }

    public void updateAllItems(List<GoodsReceivedItemRequestDto> itemList) {
        goodsReceivedItems.clear();

        addAllItems(itemList);
    }

    public List<GoodsReceivedItem> getItems() {
        return Collections.unmodifiableList(goodsReceivedItems);
    }
}
