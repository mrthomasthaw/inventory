package com.mrthaw.inventory_backend.master_data.business_partner.dto;

import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessPartnerSearchRequestDto extends CommonPageSearchRequestDto {

    private String code;

    private String name;

    private boolean isCustomer = false;

    private Integer status;

    private String address;



}
