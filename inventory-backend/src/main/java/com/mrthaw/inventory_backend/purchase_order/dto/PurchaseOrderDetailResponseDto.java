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
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDetailResponseDto {

    private Long id;

    private String purchaseOrderNo;

    private Long supplierId;

    private LocalDate purchaseDate;

    private FormStatus formStatus;

    private BigDecimal totalAmount;

    private BigDecimal totalQty;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private List<PurchaseOrderItemRequestDto> itemList = new ArrayList<>();

    public static PurchaseOrderDetailResponseDto toDto(PurchaseOrder entity) {

        return PurchaseOrderDetailResponseDto.builder()
                .id(entity.getId())
                .purchaseDate(entity.getPurchaseDate())
                .purchaseOrderNo(entity.getPurchaseOrderNo())
                .supplierId(entity.getSupplier() != null ? entity.getSupplier().getId() : null)
                .formStatus(FormStatus.getByCode(entity.getFormStatus())
                        .orElse(null))
                .totalQty(entity.getTotalQty())
                .totalAmount(entity.getTotalAmount())
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .itemList(entity.getItems()
                        .stream()
                        .map(PurchaseOrderItemRequestDto::toDto)
                        .toList())
                .build();
    }
}
