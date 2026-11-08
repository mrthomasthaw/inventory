package com.mrthaw.inventory_backend.sale_order.dto;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SaleOrderResponseDto {

    private Long id;

    private String saleOrderNo;

    private LocalDate saleOrderDate;

    private Long customerId;

    private String customerName;

    private FormStatus formStatus;


    private List<SaleOrderRequestItemDto> itemList;

    private BigDecimal totalAmount;

    private BigDecimal totalQty;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static SaleOrderResponseDto toDto(SaleOrder entity) {
        return SaleOrderResponseDto.builder()
                .id(entity.getId())
                .saleOrderNo(entity.getSaleOrderNo())
                .saleOrderDate(entity.getSaleOrderDate())
                .customerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null)
                .customerName(entity.getCustomer() != null ? entity.getCustomer().getName() : null)
                .itemList(entity.getAllItems()
                        .stream()
                        .map(SaleOrderRequestItemDto::toDto)
                        .toList())
                .formStatus(FormStatus.getByCode(entity.getFormStatus())
                        .orElse(null))
                .totalQty(entity.getTotalQty())
                .totalAmount(entity.getTotalAmount())
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}
