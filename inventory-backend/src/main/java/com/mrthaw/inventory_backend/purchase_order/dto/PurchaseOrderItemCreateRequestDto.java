package com.mrthaw.inventory_backend.purchase_order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PurchaseOrderItemCreateRequestDto {

    @NotNull(message = "Item is required")
    private Long itemId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @DecimalMin(value = "0.01")
    private BigDecimal qty;
}
