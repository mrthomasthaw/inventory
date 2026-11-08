package com.mrthaw.inventory_backend.goods_received.event;

import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceivedItem;

import java.math.BigDecimal;

public record GoodsReceivedEventItem(
        Long itemId,
        BigDecimal qty
) {
    public static GoodsReceivedEventItem toEventItem(GoodsReceivedItem item) {
        return new GoodsReceivedEventItem(item.getItem().getId(), item.getQty());
    }
}
