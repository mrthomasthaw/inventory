package com.mrthaw.inventory_backend.master_data.user_account.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.user_account.entity.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAccountResponseDto {

    private Long id;

    private String name;

    private String profileName;

    private RecordStatus status;

    private String userRole;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static UserAccountResponseDto toDto(UserAccount entity) {
        return builder()
                .id(entity.getId())
                .name(entity.getName())
                .profileName(entity.getProfileName())
                .status(RecordStatus.getByCode(entity.getStatus())
                        .orElse(null))
                .userRole(entity.getUserRole() != null ? entity.getUserRole().getName() : null)
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}
