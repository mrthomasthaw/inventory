package com.mrthaw.inventory_backend.master_data.batch.service;

import com.mrthaw.inventory_backend.master_data.batch.dto.BatchCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchResponseDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface BatchService {
    List<BatchResponseDto> getAllBatches();

    Long createBatch(@Valid BatchCreateRequestDto createRequestDto);

    void updateBatch(@Valid BatchUpdateRequestDto updateRequestDto);
}
