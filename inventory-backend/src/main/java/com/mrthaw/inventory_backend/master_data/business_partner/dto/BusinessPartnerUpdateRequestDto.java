package com.mrthaw.inventory_backend.master_data.business_partner.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BusinessPartnerUpdateRequestDto {

    @NotNull(message = "Id is required")
    private Long id;

    private String code;

    private String name;

    private String address;

    private Integer status;

    private Boolean isCustomer;
}
