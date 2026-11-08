package com.mrthaw.inventory_backend.purchase_order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrderItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PurchaseOrderItemRequestDto extends CommonItemListRequestDto {

    private Long id;

//    @NotNull(message = "Item is required")
//    private Long itemId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "1")
    private BigDecimal price;

    private BigDecimal receivedQty;

//    @DecimalMin(value = "0.01")
//    private BigDecimal qty;

    public static PurchaseOrderItemRequestDto toDto(PurchaseOrderItem entity) {
        return PurchaseOrderItemRequestDto.builder()
                .id(entity.getId())
                .itemId(entity.getItem().getId())
                .price(entity.getPrice())
                .qty(entity.getQty())
                .receivedQty(entity.getReceivedQty() == null ? BigDecimal.ZERO : entity.getReceivedQty())
                .build();
    }
}
