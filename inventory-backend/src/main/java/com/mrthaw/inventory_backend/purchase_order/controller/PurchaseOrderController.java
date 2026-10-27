package com.mrthaw.inventory_backend.purchase_order.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderCreateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderResponseDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderSearchRequestDto;
import com.mrthaw.inventory_backend.purchase_order.dto.PurchaseOrderUpdateRequestDto;
import com.mrthaw.inventory_backend.purchase_order.service.PurchaseOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    @PostMapping("/search")
    public ApiResponse<PageResult<PurchaseOrderResponseDto>> searchPurchaseOrders(@RequestBody PurchaseOrderSearchRequestDto searchRequestDto) {
        return ApiResponse.success(purchaseOrderService.searchPurchaseOrders(searchRequestDto));
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createPurchaseOrder(@Valid @RequestBody PurchaseOrderCreateRequestDto createRequestDto) {
        return ApiResponse.success(Map.of("purchaseOrderId", purchaseOrderService.createPurchaseOrder(createRequestDto)));
    }

    @PatchMapping
    public ApiResponse<Void> updatePurchaseOrder(@Valid @RequestBody PurchaseOrderUpdateRequestDto updateRequestDto) {
        purchaseOrderService.updatePurchaseOrder(updateRequestDto);
        return ApiResponse.success();
    }
}
