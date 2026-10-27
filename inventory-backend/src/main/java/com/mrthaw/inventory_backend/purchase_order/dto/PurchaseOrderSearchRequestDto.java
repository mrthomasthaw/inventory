package com.mrthaw.inventory_backend.purchase_order.dto;

import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PurchaseOrderSearchRequestDto extends CommonPageSearchRequestDto {

    private String purchaseOrderNo;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Long supplierId;

    private Integer formStatus;
}
