package com.mrthaw.inventory_backend.master_data.business_partner.service;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerCreateRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerResponseDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerUpdateRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import com.mrthaw.inventory_backend.master_data.business_partner.jpa_specification.BusinessPartnerSpecification;
import com.mrthaw.inventory_backend.master_data.business_partner.repo.BusinessPartnerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BusinessPartnerServiceImpl implements BusinessPartnerService {

    private final BusinessPartnerRepo businessPartnerRepo;

    @Override
    public Long createBusinessPartners(BusinessPartnerCreateRequestDto createRequestDto) {
        BusinessPartner businessPartner = BusinessPartner.builder()
                .code(createRequestDto.getCode())
                .name(createRequestDto.getName())
                .address(createRequestDto.getAddress())
                .status(createRequestDto.getStatus())
                .isCustomer(createRequestDto.getIsCustomer())
                .build();

        businessPartnerRepo.save(businessPartner);
        return businessPartner.getId();
    }

    @Override
    public void updateBusinessPartners(BusinessPartnerUpdateRequestDto updateRequestDto) {
        BusinessPartner businessPartner = businessPartnerRepo.findById(updateRequestDto.getId())
                .orElseThrow(() -> new DataNotFoundException("Business partner not found"));

        if(CommonUtils.isValidString(updateRequestDto.getName()))
            businessPartner.setName(updateRequestDto.getName());

        if(CommonUtils.isValidString(updateRequestDto.getCode()))
            businessPartner.setCode(updateRequestDto.getCode());

        if(CommonUtils.isValidString(updateRequestDto.getAddress()))
            businessPartner.setAddress(updateRequestDto.getAddress());

        if(updateRequestDto.getIsCustomer() != null)
            businessPartner.setIsCustomer(updateRequestDto.getIsCustomer());

        if(updateRequestDto.getStatus() != null)
            businessPartner.setStatus(updateRequestDto.getStatus());

        businessPartnerRepo.save(businessPartner);
    }

    @Override
    public List<BusinessPartnerResponseDto> getAllBusinessPartners() {
        return businessPartnerRepo.findAll()
                .stream()
                .map(BusinessPartnerResponseDto::toDto)
                .toList();
    }

    @Override
    public PageResult<BusinessPartnerResponseDto> searchBusinessPartners(BusinessPartnerSearchRequestDto searchRequestDto) {

        return PageResult.toPageResult(businessPartnerRepo.findAll(BusinessPartnerSpecification.createSearchSpecification(searchRequestDto),
                PageResult.createPageable(searchRequestDto, Sort.by("id").descending())), BusinessPartnerResponseDto::toDto);
    }

}
