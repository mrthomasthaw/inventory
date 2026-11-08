package com.mrthaw.inventory_backend.goods_received.dto;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsReceivedDetailResponseDto {

    private Long id;

    private String goodsReceivedNo;

    private LocalDate goodsReceivedDate;

    private Long purchaseOrderId;

    private FormStatus formStatus;

    private List<GoodsReceivedItemRequestDto> itemList = new ArrayList<>();

    public static GoodsReceivedDetailResponseDto toDto(GoodsReceived entity) {
        return GoodsReceivedDetailResponseDto.builder()
                .id(entity.getId())
                .goodsReceivedDate(entity.getGoodsReceivedDate())
                .goodsReceivedNo(entity.getGoodsReceivedNo())
                .purchaseOrderId(entity.getPurchaseOrderId())
                .formStatus(FormStatus.getByCode(entity.getFormStatus())
                        .orElse(null))
                .itemList(entity.getItems()
                        .stream()
                        .map(GoodsReceivedItemRequestDto::toDto)
                        .toList())
                .build();
    }
}
