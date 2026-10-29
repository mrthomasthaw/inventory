package com.mrthaw.inventory_backend.purchase_order.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.purchase_order.dto.*;
import jakarta.validation.Valid;

public interface PurchaseOrderService {

    PageResult<PurchaseOrderResponseDto> searchPurchaseOrders(PurchaseOrderSearchRequestDto searchRequestDto);

    String createPurchaseOrder(@Valid PurchaseOrderCreateRequestDto createRequestDto);

    void updatePurchaseOrder(@Valid PurchaseOrderUpdateRequestDto updateRequestDto);

    PurchaseOrderDetailResponseDto findById(Long purchaseOrderId);
}
