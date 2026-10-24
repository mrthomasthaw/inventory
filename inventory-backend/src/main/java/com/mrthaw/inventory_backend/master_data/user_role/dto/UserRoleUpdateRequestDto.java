package com.mrthaw.inventory_backend.master_data.user_role.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRoleUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String name;

    private String code;

    private Integer status;
}
