package com.mrthaw.inventory_backend.goods_received.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.goods_received.dto.GoodsReceivedSearchRequestDto;
import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GoodsReceivedSpecification {

    public static Specification<GoodsReceived> createSearchSpecification(GoodsReceivedSearchRequestDto searchRequestDto) {
        return ((root, query, cb) -> {
           List<Predicate> predicates = new ArrayList<>();

           if(CommonUtils.isValidString(searchRequestDto.getPurchaseOrderNo()))
               predicates.add(cb.like(root.get("purchaseOrderNo"), "%" + searchRequestDto.getPurchaseOrderNo() + "%"));

           if(CommonUtils.isValidString(searchRequestDto.getGoodsReceivedNo()))
               predicates.add(cb.like(root.get("goodsReceivedNo"), "%" + searchRequestDto.getGoodsReceivedNo() + "%"));

           if(searchRequestDto.getFormStatus() != null)
               predicates.add(cb.equal(root.get("formStatus"), searchRequestDto.getFormStatus()));

           if(searchRequestDto.getFromDate() != null)
               predicates.add(cb.greaterThanOrEqualTo(root.get("goodsReceivedDate"), searchRequestDto.getFromDate()));

           if(searchRequestDto.getToDate() != null)
               predicates.add(cb.lessThanOrEqualTo(root.get("goodsReceivedDate"), searchRequestDto.getToDate()));

           return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}
