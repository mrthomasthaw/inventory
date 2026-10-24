package com.mrthaw.inventory_backend.master_data.batch.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchResponseDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.service.BatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/batches")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @GetMapping
    public ApiResponse<List<BatchResponseDto>> getBatches() {
        return ApiResponse.success(batchService.getAllBatches());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createBatch(@Valid @RequestBody BatchCreateRequestDto createRequestDto) {
        Long id = batchService.createBatch(createRequestDto);
        return ApiResponse.success(Map.of("batchId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateBatch(@Valid @RequestBody BatchUpdateRequestDto updateRequestDto) {
        batchService.updateBatch(updateRequestDto);
        return ApiResponse.success();
    }
}
