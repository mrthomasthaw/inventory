package com.mrthaw.inventory_backend.purchase_order.api;

import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderDetailResponseDto;
import jakarta.persistence.LockModeType;

import java.util.Optional;

public interface PurchaseOrderApi {

    PurchaseOrderDetailResponseDto findById(Long purchaseOrderId) throws DataNotFoundException;


    PurchaseOrderDetailResponseDto findById(Long purchaseOrderId, LockModeType lockModeType) throws DataNotFoundException;

}
