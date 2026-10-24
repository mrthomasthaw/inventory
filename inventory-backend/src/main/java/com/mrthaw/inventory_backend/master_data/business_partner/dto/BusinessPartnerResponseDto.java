package com.mrthaw.inventory_backend.master_data.business_partner.dto;

import com.mrthaw.inventory_backend.common.enum_constant.RecordStatus;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessPartnerResponseDto {

    private Long id;

    private String code;

    private String name;

    private String address;

    private RecordStatus status;

    private Boolean isCustomer;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static BusinessPartnerResponseDto toDto(BusinessPartner entity) {
        return BusinessPartnerResponseDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .address(entity.getAddress())
                .isCustomer(entity.getIsCustomer())
                .status(RecordStatus.getByCode(entity.getStatus())
                        .orElse(null))
                .createdTime(entity.getCreatedTime())
                .updatedTime(entity.getUpdatedTime())
                .build();
    }
}
