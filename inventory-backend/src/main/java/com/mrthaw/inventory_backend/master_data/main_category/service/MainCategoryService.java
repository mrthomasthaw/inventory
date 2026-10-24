package com.mrthaw.inventory_backend.master_data.main_category.service;

import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryResponseDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface MainCategoryService {
    List<MainCategoryResponseDto> getAllMainCategories();

    Long createMainCategory(@Valid MainCategoryCreateRequestDto createRequestDto);

    void updateMainCategory(@Valid MainCategoryUpdateRequestDto updateRequestDto);
}
