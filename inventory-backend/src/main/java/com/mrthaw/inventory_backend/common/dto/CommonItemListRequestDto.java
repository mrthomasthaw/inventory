package com.mrthaw.inventory_backend.common.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommonItemListRequestDto {

    @NotNull(message = "Item is required")
    private Long itemId;

    @NotNull(message = "Qty is required")
    @DecimalMin(value = "0.01")
    private BigDecimal qty;

}
