package com.mrthaw.inventory_backend.goods_issued.event;

import java.time.LocalDate;
import java.util.List;

public record GoodsIssuedSubmitEvent (
        long id,
        String goodsIssuedNo,
        LocalDate goodsIssuedDate,
        String referenceFormNo,
        List<GoodsIssuedEventItem> itemList
) {
}
