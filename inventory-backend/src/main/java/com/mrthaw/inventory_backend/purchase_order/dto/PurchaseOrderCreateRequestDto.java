package com.mrthaw.inventory_backend.purchase_order.dto;

import com.mrthaw.inventory_backend.purchase_order.validator.annotation.UniqueItemList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class PurchaseOrderCreateRequestDto {

    @NotNull(message = "Purchase date is required")
    private LocalDate purchaseDate;

    @NotNull(message = "Supplier is required")
    private Long supplierId;

    @Valid
    @NotEmpty(message = "Purchase order item is required")
    @UniqueItemList(message = "Duplicate item list")
    private List<PurchaseOrderItemCreateRequestDto> itemList = new ArrayList<>();
}
