package com.mrthaw.inventory_backend.master_data.unit.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchResponseDto;
import com.mrthaw.inventory_backend.master_data.batch.entity.Batch;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitResponseDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.entity.Unit;
import com.mrthaw.inventory_backend.master_data.unit.repo.UnitRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Validated
public class UnitServiceImpl implements UnitService {

    private final UnitRepo unitRepo;

    @Override
    public List<UnitResponseDto> getAllUnits() {
        return unitRepo.findAll()
                .stream()
                .map(UnitResponseDto::toDto)
                .toList();
    }

    @Override
    public Long createUnit(@Valid UnitCreateRequestDto createRequestDto) {
        Unit unit = Unit.builder()
                .code(createRequestDto.getCode())
                .name(createRequestDto.getName())
                .status(createRequestDto.getStatus())
                .build();

        unitRepo.save(unit);
        return unit.getId();
    }

    @Override
    public void updateUnit(@Valid UnitUpdateRequestDto updateRequestDto) {
        Unit unit = unitRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Unit not found"));

        if(CommonUtils.isValidString(updateRequestDto.getCode()))
            unit.setCode(updateRequestDto.getCode());

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            unit.setName(updateRequestDto.getName());

        if(updateRequestDto.getStatus() != null)
            unit.setStatus(updateRequestDto.getStatus());

        unitRepo.save(unit);
    }
}
