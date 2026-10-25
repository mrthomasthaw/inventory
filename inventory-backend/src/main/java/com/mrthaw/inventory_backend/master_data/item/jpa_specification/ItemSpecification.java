package com.mrthaw.inventory_backend.master_data.item.jpa_specification;

import com.mrthaw.inventory_backend.common.util.CommonUtils;
import com.mrthaw.inventory_backend.master_data.item.dto.ItemSearchRequestDto;
import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecification {

    public static Specification<Item> createSearchSpecification(ItemSearchRequestDto searchRequestDto) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (CommonUtils.isValidString(searchRequestDto.getItemName()))
                predicates.add(cb.like(root.get("itemName").as(String.class), "%" + searchRequestDto.getItemName() + "%"));

            if (CommonUtils.isValidString(searchRequestDto.getItemCode()))
                predicates.add(cb.like(root.get("itemCode").as(String.class), "%" + searchRequestDto.getItemCode() + "%"));

            if(searchRequestDto.getStatus() != null)
                predicates.add(cb.equal(root.get("status"), searchRequestDto.getStatus()));

            if (searchRequestDto.getMainCategoryId() != null)
                predicates.add(cb.equal(root.get("mainCategory").get("id"), searchRequestDto.getMainCategoryId()));

            if (searchRequestDto.getUnitId() != null)
                predicates.add(cb.equal(root.get("unit").get("id"), searchRequestDto.getUnitId()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
