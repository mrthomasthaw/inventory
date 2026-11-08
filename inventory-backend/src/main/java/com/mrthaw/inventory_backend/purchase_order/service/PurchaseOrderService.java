package com.mrthaw.inventory_backend.purchase_order.service;

import com.mrthaw.inventory_backend.common.dto.CommonItemListRequestDto;
import com.mrthaw.inventory_backend.common.enum_constant.FormStatus;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_received.event.GoodsReceivedEventItem;
import com.mrthaw.inventory_backend.purchase_order.dto.*;
import jakarta.persistence.LockModeType;
import jakarta.validation.Valid;

import java.util.List;

public interface PurchaseOrderService {

    PageResult<PurchaseOrderResponseDto> searchPurchaseOrders(PurchaseOrderSearchRequestDto searchRequestDto);

    String createPurchaseOrder(@Valid PurchaseOrderCreateRequestDto createRequestDto, String requestId);

    void updatePurchaseOrder(@Valid PurchaseOrderUpdateRequestDto updateRequestDto);

    PurchaseOrderDetailResponseDto findById(Long purchaseOrderId);

    PurchaseOrderDetailResponseDto findById(Long purchaseOrderId,  LockModeType lockModeType) throws DataNotFoundException;

    void updatePurchaseOrderOnGoodsReceived(Long purchaseOrderId, List<GoodsReceivedEventItem> itemList);
}
