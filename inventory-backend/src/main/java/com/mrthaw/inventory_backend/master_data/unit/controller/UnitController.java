package com.mrthaw.inventory_backend.master_data.unit.controller;


import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchResponseDto;
import com.mrthaw.inventory_backend.master_data.batch.dto.BatchUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitResponseDto;
import com.mrthaw.inventory_backend.master_data.unit.dto.UnitUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.unit.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/units")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    public ApiResponse<List<UnitResponseDto>> getUnits() {
        return ApiResponse.success(unitService.getAllUnits());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createUnit(@Valid @RequestBody UnitCreateRequestDto createRequestDto) {
        Long id = unitService.createUnit(createRequestDto);
        return ApiResponse.success(Map.of("unitId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateUnit(@Valid @RequestBody UnitUpdateRequestDto updateRequestDto) {
        unitService.updateUnit(updateRequestDto);
        return ApiResponse.success();
    }
}
