package com.mrthaw.inventory_backend.goods_received;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class GoodsReceivedItemRequestDto {

    private Long id;

    private Long itemId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal qty;

}