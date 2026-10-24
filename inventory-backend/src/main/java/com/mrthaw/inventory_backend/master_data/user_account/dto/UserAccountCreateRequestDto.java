package com.mrthaw.inventory_backend.master_data.user_account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserAccountCreateRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Profile name is required")
    private String profileName;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;

    @NotNull(message = "Status is required")
    private Integer status;

    @NotNull(message = "User Role is required")
    private Long userRoleId;
}
