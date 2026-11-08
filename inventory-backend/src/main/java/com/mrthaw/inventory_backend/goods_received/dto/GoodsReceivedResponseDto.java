package com.mrthaw.inventory_backend.goods_received.dto;


import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GoodsReceivedResponseDto {

    private Long id;

    private String goodsReceivedNo;

    private LocalDate goodsReceivedDate;

    private Long purchaseOrderId;

    private FormStatus formStatus;

    public static GoodsReceivedResponseDto toDto(GoodsReceived entity) {
        return GoodsReceivedResponseDto.builder()
                .id(entity.getId())
                .goodsReceivedNo(entity.getGoodsReceivedNo())
                .goodsReceivedDate(entity.getGoodsReceivedDate())
                .purchaseOrderId(entity.getPurchaseOrderId())
                .formStatus(FormStatus.getByCode(entity.getFormStatus())
                        .orElse(null))
                .build();
    }

    @Override
    public String toString() {
        return "GoodsReceivedResponseDto{" +
                "id=" + id +
                ", goodsReceivedNo='" + goodsReceivedNo + '\'' +
                ", goodsReceivedDate=" + goodsReceivedDate +
                ", purchaseOrderId=" + purchaseOrderId +
                ", formStatus=" + formStatus +
                '}';
    }
}
