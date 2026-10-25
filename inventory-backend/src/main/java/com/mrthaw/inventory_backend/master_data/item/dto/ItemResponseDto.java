package com.mrthaw.inventory_backend.master_data.item.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto {

    private Long id;

    private String itemName;

    private String itemCode;

    private String description;

    private RecordStatus status;

    private String mainCategory;

    private String unit;

    public static ItemResponseDto toDto(Item entity) {
        return ItemResponseDto.builder()
                .id(entity.getId())
                .itemName(entity.getItemName())
                .itemCode(entity.getItemCode())
                .status(RecordStatus.getByCode(entity.getStatus())
                        .orElse(null))
                .description(entity.getDescription())
                .mainCategory(entity.getMainCategory() != null ? entity.getMainCategory().getName() : null)
                .unit(entity.getUnit() != null ? entity.getUnit().getName() : null)
                .build();

    }
}
