package com.mrthaw.inventory_backend.goods_issued.dto;

import com.mrthaw.inventory_backend.common.validator.UniqueItemList;
import jakarta.validation.Valid;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GoodsIssuedCreateRequestDto {

    private String referenceFormNo;

    private LocalDate goodsIssuedDate;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @Valid
    @UniqueItemList(message = "Duplicate item list")
    private List<GoodsIssuedItemRequestDto> itemList;

    private Long saleOrderId;

}
