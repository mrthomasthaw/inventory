package com.mrthaw.inventory_backend.master_data.main_category.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryResponseDto;
import com.mrthaw.inventory_backend.master_data.main_category.dto.MainCategoryUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.main_category.entity.MainCategory;
import com.mrthaw.inventory_backend.master_data.main_category.repo.MainCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MainCategoryServiceImpl implements MainCategoryService {

    private final MainCategoryRepo mainCategoryRepo;

    @Override
    public List<MainCategoryResponseDto> getAllMainCategories() {
        return mainCategoryRepo.findAll()
                .stream()
                .map(MainCategoryResponseDto::toDto)
                .toList();
    }

    @Override
    public Long createMainCategory(MainCategoryCreateRequestDto createRequestDto) {
        MainCategory mainCategory = MainCategory.builder()
                .code(createRequestDto.getCode())
                .name(createRequestDto.getName())
                .status(createRequestDto.getStatus())
                .build();

        mainCategoryRepo.save(mainCategory);
        return mainCategory.getId();
    }

    @Override
    public void updateMainCategory(MainCategoryUpdateRequestDto updateRequestDto) {
        MainCategory mainCategory = mainCategoryRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Main category not found"));

        if(CommonUtils.isValidString(updateRequestDto.getCode()))
            mainCategory.setCode(updateRequestDto.getCode());

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            mainCategory.setName(updateRequestDto.getName());

        if(updateRequestDto.getStatus() != null)
            mainCategory.setStatus(updateRequestDto.getStatus());

        mainCategoryRepo.save(mainCategory);
    }
}
