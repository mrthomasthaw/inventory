package com.mrthaw.inventory_backend.master_data.user_account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserAccountUpdateRequestDto {

    @NotNull
    private Long id;

    private String name;

    private String profileName;

    private Integer status;

    private Long userRoleId;
}
