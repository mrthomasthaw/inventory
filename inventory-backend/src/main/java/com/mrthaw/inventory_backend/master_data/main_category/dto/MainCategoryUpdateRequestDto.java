package com.mrthaw.inventory_backend.master_data.main_category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MainCategoryUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String name;

    private String code;

    private Integer status;
}
