package com.mrthaw.inventory_backend.goods_issued.dto;


import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class GoodsIssuedSearchRequestDto extends CommonPageSearchRequestDto {

    private String referenceFormNo;

    private LocalDate fromDate;

    private LocalDate toDate;

    private String goodsIssuedNo;

}
