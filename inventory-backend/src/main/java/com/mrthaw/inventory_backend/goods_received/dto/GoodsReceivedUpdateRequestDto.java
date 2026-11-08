package com.mrthaw.inventory_backend.goods_received.dto;


import com.mrthaw.inventory_backend.common.validator.UniqueItemList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GoodsReceivedUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String goodsReceivedNo;

    private LocalDate goodsReceivedDate;

    private Long purchaseOrderId;

    @Valid
    @UniqueItemList
    private List<GoodsReceivedItemRequestDto> itemList = new ArrayList<>();
}
