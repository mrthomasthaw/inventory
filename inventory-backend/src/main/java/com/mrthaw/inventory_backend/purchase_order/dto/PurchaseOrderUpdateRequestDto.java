package com.mrthaw.inventory_backend.purchase_order.dto;

import com.mrthaw.inventory_backend.purchase_order.validator.annotation.UniqueItemList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PurchaseOrderUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private LocalDate purchaseDate;

    private Long supplierId;

    @Valid
    @UniqueItemList(message = "Duplicate item list")
    private List<PurchaseOrderItemCreateRequestDto> itemList = new ArrayList<>();
}
