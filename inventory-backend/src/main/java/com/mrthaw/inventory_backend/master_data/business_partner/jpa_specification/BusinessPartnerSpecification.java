package com.mrthaw.inventory_backend.master_data.business_partner.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.business_partner.dto.BusinessPartnerSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BusinessPartnerSpecification {

    public static Specification<BusinessPartner> createSearchSpecification(BusinessPartnerSearchRequestDto searchRequestDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (CommonUtils.isValidString(searchRequestDto.getName())) {
                predicates.add(cb.like(root.get("name").as(String.class), "%" + searchRequestDto.getName() + "%"));
            }

            if (CommonUtils.isValidString(searchRequestDto.getCode())) {
                predicates.add(cb.like(root.get("code").as(String.class), "%" + searchRequestDto.getCode() + "%"));
            }

            if (CommonUtils.isValidString(searchRequestDto.getAddress())) {
                predicates.add(cb.like(root.get("address").as(String.class), "%" + searchRequestDto.getAddress() + "%"));
            }

            if (searchRequestDto.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), searchRequestDto.getStatus()));
            }

            predicates.add(cb.equal(root.get("isCustomer"), searchRequestDto.isCustomer()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
