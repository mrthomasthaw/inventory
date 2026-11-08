package com.mrthaw.inventory_backend.common.validator;

import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueItemListValidator implements ConstraintValidator<UniqueItemList, List<? extends CommonItemListRequestDto>> {


    @Override
    public boolean isValid(List<? extends CommonItemListRequestDto> itemList, ConstraintValidatorContext context) {
        if(itemList == null || itemList.isEmpty()) return false;

        Set<Long> itemIds = new HashSet<>();

        for(CommonItemListRequestDto item : itemList) {
            if(itemIds.contains(item.getItemId())) return false;

            itemIds.add(item.getItemId());
        }
        return true;
    }
}
