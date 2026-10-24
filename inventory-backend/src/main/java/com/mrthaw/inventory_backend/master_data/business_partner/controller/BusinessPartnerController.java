package com.mrthaw.inventory_backend.master_data.business_partner.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerResponseDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.service.BusinessPartnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/business-partners")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class BusinessPartnerController {

    private final BusinessPartnerService businessPartnerService;

    @GetMapping
    public ApiResponse<List<BusinessPartnerResponseDto>> getBusinessPartners() {
        return ApiResponse.success(businessPartnerService.getAllBusinessPartners());
    }

    @PostMapping("/search")
    public ApiResponse<PageResult<BusinessPartnerResponseDto>> searchBusinessPartners(@RequestBody BusinessPartnerSearchRequestDto searchRequestDto) {
        return ApiResponse.success(businessPartnerService.searchBusinessPartners(searchRequestDto));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Map<String, Long>> createBusinessPartner(@Valid @RequestBody BusinessPartnerCreateRequestDto createRequestDto) {
        Long id = businessPartnerService.createBusinessPartners(createRequestDto);
        return ApiResponse.success(Map.of("businessPartnerId", id));
    }

    @PatchMapping
    public ApiResponse<Void> updateBusinessPartner(@Valid @RequestBody BusinessPartnerUpdateRequestDto updateRequestDto) {
        businessPartnerService.updateBusinessPartners(updateRequestDto);
        return ApiResponse.success();
    }
}
