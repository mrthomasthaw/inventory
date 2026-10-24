package com.mrthaw.inventory_backend.master_data.unit.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;

import com.mrthaw.inventory_backend.master_data.unit.entity.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitResponseDto {

    private Long id;

    private String name;

    private String code;

    private RecordStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static UnitResponseDto toDto(Unit entity) {
        return UnitResponseDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .status(RecordStatus.getByCode(entity.getStatus())
                        .orElse(null))
                .createTime(entity.getCreatedTime())
                .updateTime(entity.getUpdatedTime())
                .build();
    }
}
