package com.mrthaw.inventory_backend.master_data.item.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ItemUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String itemName;

    private String itemCode;

    private String description;

    private Integer status;

    private Long mainCategoryId;

    private Long unitId;
}
