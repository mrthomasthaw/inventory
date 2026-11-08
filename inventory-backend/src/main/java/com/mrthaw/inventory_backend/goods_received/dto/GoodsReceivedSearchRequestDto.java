package com.mrthaw.inventory_backend.goods_received.dto;

import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class GoodsReceivedSearchRequestDto extends CommonPageSearchRequestDto {

    private String purchaseOrderNo;

    private String goodsReceivedNo;

    private Integer formStatus;

    private LocalDate fromDate;

    private LocalDate toDate;

}
