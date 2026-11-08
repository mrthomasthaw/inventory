package com.mrthaw.inventory_backend.sale_order.dto;

import com.mrthaw.inventory_backend.common.validator.UniqueItemList;
import jakarta.validation.Valid;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SaleOrderUpdateRequestDto {

    private Long id;

    private Long customerId;

    private LocalDate saleOrderDate;

    @Valid
    @UniqueItemList(message = "Duplicate item list")
    private List<SaleOrderRequestItemDto> itemList = new ArrayList<>();
}
