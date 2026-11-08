package com.mrthaw.inventory_backend.goods_received.dto;


import com.mrthaw.inventory_backend.common.validator.UniqueItemList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GoodsReceivedCreateRequestDto {

    @NotNull(message = "Goods Received Date is required")
    private LocalDate goodsReceivedDate;

    @NotNull(message = "Purchase Order is required")
    private Long purchaseOrderId;

    @Valid
    @NotEmpty(message = "Goods Received List is required")
    @UniqueItemList
    private List<GoodsReceivedItemRequestDto> itemList = new ArrayList<>();
}
