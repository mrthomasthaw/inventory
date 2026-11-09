package com.mrthaw.inventory_backend.goods_issued.dto;

import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssuedItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsIssuedItemRequestDto extends CommonItemListRequestDto {

    private Long id;

    @NotNull(message = "price is required")
    @DecimalMin(value = "1", message = "Value must be at least 1")
    private BigDecimal price;

    private BigDecimal totalAmount;

    public static GoodsIssuedItemRequestDto toDto(GoodsIssuedItem goodsIssuedItem) {
        return GoodsIssuedItemRequestDto
                .builder()
                .id(goodsIssuedItem.getId())
                .itemId(goodsIssuedItem.getItem().getId())
                .qty(goodsIssuedItem.getQty())
                .totalAmount(goodsIssuedItem.getTotalAmount())
                .build();
    }
}
