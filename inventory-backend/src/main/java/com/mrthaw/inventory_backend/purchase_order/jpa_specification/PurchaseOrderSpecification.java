package com.mrthaw.inventory_backend.purchase_order.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderSearchRequestDto;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderSpecification {

    public static Specification<PurchaseOrder> createSearchSpecification(PurchaseOrderSearchRequestDto searchRequestDto) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(CommonUtils.isValidString(searchRequestDto.getPurchaseOrderNo()))
                predicates.add(cb.like(root.get("purchaseOrderNo").as(String.class), "%" + searchRequestDto.getPurchaseOrderNo() + "%"));

            if (searchRequestDto.getFromDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.<LocalDate>get("purchaseDate"), searchRequestDto.getFromDate()));

            if (searchRequestDto.getToDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.<LocalDate>get("purchaseDate"), searchRequestDto.getToDate()));

            if (searchRequestDto.getFormStatus() != null)
                predicates.add(cb.equal(root.get("formStatus"), searchRequestDto.getFormStatus()));

            if (searchRequestDto.getSupplierId() != null)
                predicates.add(cb.equal(root.get("supplier").get("id"), searchRequestDto.getSupplierId()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
