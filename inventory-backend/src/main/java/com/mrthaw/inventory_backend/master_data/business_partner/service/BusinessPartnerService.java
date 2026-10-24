package com.mrthaw.inventory_backend.master_data.business_partner.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerResponseDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerUpdateRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface BusinessPartnerService {

    Long createBusinessPartners(@Valid BusinessPartnerCreateRequestDto createRequestDto);

    void updateBusinessPartners(@Valid BusinessPartnerUpdateRequestDto updateRequestDto);

    List<BusinessPartnerResponseDto> getAllBusinessPartners();

    PageResult<BusinessPartnerResponseDto> searchBusinessPartners(BusinessPartnerSearchRequestDto searchRequestDto);
}
