package com.mrthaw.inventory_backend.master_data.user_role.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponseDto {

    private Long id;

    private String name;

    private String code;

    private RecordStatus status;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static UserRoleResponseDto toDto(UserRole entity) {
        return UserRoleResponseDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .status(RecordStatus.getByCode(entity.getStatus())
                        .orElse(null))
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}
