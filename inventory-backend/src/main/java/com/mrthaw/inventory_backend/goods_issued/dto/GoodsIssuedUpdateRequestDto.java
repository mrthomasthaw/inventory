package com.mrthaw.inventory_backend.goods_issued.dto;

import com.mrthaw.inventory_backend.common.validator.UniqueItemList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GoodsIssuedUpdateRequestDto {

    private Long id;

    private LocalDate goodsIssuedDate;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @UniqueItemList(message = "Duplicate item list")
    private List<GoodsIssuedItemRequestDto> itemList;

    private Long saleOrderId;
}
