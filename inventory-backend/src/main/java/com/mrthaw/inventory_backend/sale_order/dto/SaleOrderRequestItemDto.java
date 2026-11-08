package com.mrthaw.inventory_backend.sale_order.dto;

import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrderItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleOrderRequestItemDto extends CommonItemListRequestDto {

    private Long id;

//    @NotNull
//    private Long itemId;
//
//    @DecimalMin(value = "1")
//    private BigDecimal qty;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @DecimalMin(value = "0.01")
    private BigDecimal totalAmount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static SaleOrderRequestItemDto toDto(SaleOrderItem item) {
        return SaleOrderRequestItemDto.builder()
                .id(item.getId())
                .itemId(item.getItem().getId())
                .qty(item.getQty())
                .price(item.getPrice())
                .totalAmount(item.getTotalAmount())
                .createTime(item.getCreatedTime())
                .updateTime(item.getUpdatedTime())
                .build();
    }

    public static SaleOrderItem toCreateEntity(SaleOrderRequestItemDto itemDto) {
        return SaleOrderItem.builder()
                .item(new Item(itemDto.getItemId()))
                .price(itemDto.getPrice())
                .qty(itemDto.getQty())
                .totalAmount(itemDto.getQty().multiply(itemDto.getPrice()))
                .build();

    }

    public static SaleOrderItem toUpdateEntity(SaleOrderRequestItemDto itemDto) {
        return SaleOrderItem.builder()
                .id(itemDto.getId())
                .item(new Item(itemDto.getItemId()))
                .qty(itemDto.getQty())
                .price(itemDto.getPrice())
                .totalAmount(itemDto.getPrice().multiply(itemDto.getQty()))
                .build();
    }
}
