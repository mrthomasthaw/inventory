package com.mrthaw.inventory_backend.purchase_order.validator;

import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderItemCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import com.mrthaw.inventory_backend.purchase_order.validator.annotation.UniqueItemList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueItemListValidator implements ConstraintValidator<UniqueItemList, List<PurchaseOrderItemCreateRequestDto>> {

    @Override
    public boolean isValid(List<PurchaseOrderItemCreateRequestDto> itemList, ConstraintValidatorContext context) {

        if(itemList == null || itemList.isEmpty())
            return false;

        Set<Long> uniqueItemList = new HashSet<>();
        for(PurchaseOrderItemCreateRequestDto item : itemList){
            if(item.getItemId() == null)
                return false;

            if(uniqueItemList.contains(item.getItemId()))
                return false;

            uniqueItemList.add(item.getItemId());
        }
        return true;
    }
}
