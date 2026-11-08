package com.mrthaw.inventory_backend.purchase_order.api;


import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderDetailResponseDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderResponseDto;
import com.mrthaw.inventory_backend.purchase_order.repo.PurchaseOrderRepo;
import com.mrthaw.inventory_backend.purchase_order.service.PurchaseOrderService;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseOrderApiImpl implements PurchaseOrderApi {

    private final PurchaseOrderService purchaseOrderService;

    @Override
    public PurchaseOrderDetailResponseDto findById(Long purchaseOrderId) throws DataNotFoundException {
        return purchaseOrderService.findById(purchaseOrderId);
    }

    @Override
    public PurchaseOrderDetailResponseDto findById(Long purchaseOrderId, LockModeType lockModeType) throws DataNotFoundException {
        return purchaseOrderService.findById(purchaseOrderId, lockModeType);
    }
}
