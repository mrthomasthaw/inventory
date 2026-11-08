package com.mrthaw.inventory_backend.goods_received.event;


import java.time.LocalDateTime;
import java.util.List;

public record GoodsReceivedCreatedEvent(
        long goodsReceivedId,
        long purchaseOrderId,
        String goodsReceivedNo,
        List<GoodsReceivedEventItem> itemList,
        LocalDateTime createdTime) {
}
