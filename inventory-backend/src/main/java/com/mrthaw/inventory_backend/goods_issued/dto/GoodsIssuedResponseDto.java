package com.mrthaw.inventory_backend.goods_issued.dto;

import com.mrthaw.inventory_backend.common.validator.UniqueItemList;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssued;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsIssuedResponseDto {

    private Long id;

    private LocalDate goodsIssuedDate;

    private String goodsIssuedNo;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private List<GoodsIssuedItemRequestDto> itemList;

    private Long saleOrderId;

    private String referenceFormNo;


    public static GoodsIssuedResponseDto toDto(GoodsIssued goodsIssued) {
        return GoodsIssuedResponseDto.builder()
                .id(goodsIssued.getId())
                .goodsIssuedNo(goodsIssued.getGoodsIssuedNo())
                .goodsIssuedDate(goodsIssued.getGoodsIssuedDate())
                .referenceFormNo(goodsIssued.getReferenceFormNo())
                .createdTime(goodsIssued.getCreatedTime())
                .updatedTime(goodsIssued.getUpdatedTime())
                .build();
    }
}
