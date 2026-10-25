package com.mrthaw.inventory_backend.master_data.item.dto;

import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;


@Getter
public class ItemSearchRequestDto extends CommonPageSearchRequestDto {

    private String itemName;

    private String itemCode;

    private Integer status;

    private Long mainCategoryId;

    private Long unitId;
}
