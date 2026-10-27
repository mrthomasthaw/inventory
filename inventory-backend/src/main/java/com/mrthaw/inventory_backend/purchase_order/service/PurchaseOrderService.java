package com.mrthaw.inventory_backend.purchase_order.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderResponseDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderSearchRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderUpdateRequestDto;
import jakarta.validation.Valid;

public interface PurchaseOrderService {

    PageResult<PurchaseOrderResponseDto> searchPurchaseOrders(PurchaseOrderSearchRequestDto searchRequestDto);

    String createPurchaseOrder(@Valid PurchaseOrderCreateRequestDto createRequestDto);

    void updatePurchaseOrder(@Valid PurchaseOrderUpdateRequestDto updateRequestDto);
}
