package com.mrthaw.inventory_backend.master_data.main_category.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryResponseDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.main_category.service.MainCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/main-categories")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class MainCategoryController {

    private final MainCategoryService mainCategoryService;

    @GetMapping
    public ApiResponse<List<MainCategoryResponseDto>> getMainCategories() {
        return ApiResponse.success(mainCategoryService.getAllMainCategories());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createMainCategory(@Valid @RequestBody MainCategoryCreateRequestDto createRequestDto) {
        Long id = mainCategoryService.createMainCategory(createRequestDto);
        return ApiResponse.success(Map.of("mainCategoryId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateMainCategory(@Valid @RequestBody MainCategoryUpdateRequestDto updateRequestDto) {
        mainCategoryService.updateMainCategory(updateRequestDto);
        return ApiResponse.success();
    }
}
