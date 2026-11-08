package com.mrthaw.inventory_backend.sale_order.dto;

import com.mrthaw.inventory_backend.common.util.CommonPageSearchRequestDto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SaleOrderSearchRequestDto extends CommonPageSearchRequestDto {

    private Long customerId;

    private String saleOrderNo;

    private LocalDate fromDate;

    private LocalDate toDate;

    private List<Integer> formStatus;
}
