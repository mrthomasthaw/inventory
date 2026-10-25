package com.mrthaw.inventory_backend.master_data.item.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ItemCreateRequestDto {

    @NotEmpty(message = "Item name is required")
    private String itemName;

    @NotEmpty(message = "Item code is required")
    private String itemCode;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private Integer status;

    @NotNull(message = "Main category is required")
    private Long mainCategoryId;

    @NotNull(message = "Unit is required")
    private Long unitId;

}
