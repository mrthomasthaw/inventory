package com.mrthaw.inventory_backend.master_data.batch.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.batch.entity.Batch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchResponseDto {

    private Long id;

    private Integer no;

    private String name;

    private String code;

    private RecordStatus status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static BatchResponseDto toDto(Batch entity) {
        return BatchResponseDto.builder()
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
