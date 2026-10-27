package com.mrthaw.inventory_backend.purchase_order.dto;

import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderResponseDto {

    private Long id;

    private String purchaseOrderNo;

    private String supplierName;

    private LocalDate purchaseDate;

    private FormStatus formStatus;

    private BigDecimal totalAmount;

    private BigDecimal totalQty;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static PurchaseOrderResponseDto toDto(PurchaseOrder entity) {

        return PurchaseOrderResponseDto.builder()
                .id(entity.getId())
                .purchaseDate(entity.getPurchaseDate())
                .purchaseOrderNo(entity.getPurchaseOrderNo())
                .supplierName(entity.getSupplier() != null ? entity.getSupplier().getName(): null)
                .formStatus(FormStatus.getByCode(entity.getFormStatus())
                        .orElse(null))
                .totalQty(entity.getTotalQty())
                .totalAmount(entity.getTotalAmount())
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}
