package com.mrthaw.inventory_backend.master_data.user_account.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;

@Getter
public class UserAccountSearchRequestDto extends CommonPageSearchRequestDto {

    private String name;

    private String profileName;

    private Integer status;

    private Long userRoleId;
}
