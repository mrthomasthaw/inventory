package com.mrthaw.inventory_backend.master_data.batch.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchResponseDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.entity.Batch;
import com.mrthaw.inventory_backend.master_data.batch.repo.BatchRepo;
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
public class BatchServiceImpl implements BatchService {

    private final BatchRepo batchRepo;

    @Override
    public List<BatchResponseDto> getAllBatches() {
        return batchRepo.findAll()
                .stream()
                .map(BatchResponseDto::toDto)
                .toList();
    }

    @Override
    public Long createBatch(@Valid BatchCreateRequestDto createRequestDto) {
        Batch batch = Batch.builder()
                .code(createRequestDto.getCode())
                .name(createRequestDto.getName())
                .status(createRequestDto.getStatus())
                .build();

        batchRepo.save(batch);
        return batch.getId();
    }

    @Override
    public void updateBatch(@Valid BatchUpdateRequestDto updateRequestDto) {
        Batch batch = batchRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Batch not found"));

        if(CommonUtils.isValidString(updateRequestDto.getCode()))
            batch.setCode(updateRequestDto.getCode());

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            batch.setName(updateRequestDto.getName());

        if(updateRequestDto.getStatus() != null)
            batch.setStatus(updateRequestDto.getStatus());

        batchRepo.save(batch);
    }
}
