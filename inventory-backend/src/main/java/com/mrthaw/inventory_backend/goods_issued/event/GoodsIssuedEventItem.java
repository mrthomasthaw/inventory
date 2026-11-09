package com.mrthaw.inventory_backend.goods_issued.event;

import java.math.BigDecimal;

public record GoodsIssuedEventItem(
        long itemId,
        BigDecimal qty,
        BigDecimal totalAmount
) {
}
