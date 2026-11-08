package com.mrthaw.inventory_backend.sale_order.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderSearchRequestDto;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrder;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SaleOrderSpecification {
    public static Specification<SaleOrder> createSearchSpecification(SaleOrderSearchRequestDto searchDto) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(searchDto.getCustomerId() != null)
                predicates.add(cb.equal(root.get("customer").get("id"), searchDto.getCustomerId()));

            if(searchDto.getFromDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("saleOrderDate"), searchDto.getFromDate()));

            if(searchDto.getToDate() != null)
                predicates.add(cb.equal(root.get("salOrderDate"), searchDto.getToDate()));

            if(CommonUtils.isValidList(searchDto.getFormStatus()))
                predicates.add(root.get("formStatus").in(searchDto.getFormStatus()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
