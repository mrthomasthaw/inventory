package com.mrthaw.inventory_backend.goods_issued.jpa_specification;


import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedSearchRequestDto;
import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssued;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GoodsIssuedSpecification {

    public static Specification<GoodsIssued> createSearchSpecification(GoodsIssuedSearchRequestDto searchDto) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if(searchDto.getFromDate() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("goodsIssuedDate"), searchDto.getFromDate()));

            if(searchDto.getToDate() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("goodsIssuedDate"), searchDto.getToDate()));

            if(CommonUtils.isValidString(searchDto.getGoodsIssuedNo()))
                predicates.add(cb.like(root.get("goodsIssuedNo"), "%" + searchDto.getGoodsIssuedNo() + "%"));



            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
