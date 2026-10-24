package com.mrthaw.inventory_backend.master_data.business_partner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BusinessPartnerCreateRequestDto {

    @NotBlank(message = "Code is required")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "IsCustomer is required")
    private Boolean isCustomer;

    @NotNull(message = "Status is required")
    private Integer status;
}
