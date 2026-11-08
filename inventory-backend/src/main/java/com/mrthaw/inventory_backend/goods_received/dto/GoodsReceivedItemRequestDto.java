package com.mrthaw.inventory_backend.goods_received.dto;


import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceivedItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsReceivedItemRequestDto extends CommonItemListRequestDto {

    private Long id;

//    @NotNull(message = "Item is required")
//    private Long itemId;

//    @NotNull(message = "Qty is required")
//    @DecimalMin(value = "0.01")
//    private BigDecimal qty;

    public static GoodsReceivedItemRequestDto toDto(GoodsReceivedItem item) {
        return GoodsReceivedItemRequestDto.builder()
                .id(item.getId())
                .itemId(item.getItem().getId())
                .qty(item.getQty())
                .build();
    }
}