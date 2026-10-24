package com.mrthaw.inventory_backend.master_data.batch.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BatchUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String name;

    private String code;

    private Integer status;
}
