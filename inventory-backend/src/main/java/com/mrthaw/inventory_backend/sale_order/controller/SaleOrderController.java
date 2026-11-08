package com.mrthaw.inventory_backend.sale_order.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderCreateRequestDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderSearchRequestDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderUpdateRequestDto;
import com.mrthaw.inventory_backend.sale_order.service.SaleOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale-orders")
@CrossOrigin(origins = "http://localhost:8080")
public class SaleOrderController {

    private final SaleOrderService saleOrderService;


    @GetMapping("/{id}")
    public ApiResponse<SaleOrderResponseDto> getSaleOrder(@PathVariable Long id) {
        return ApiResponse.success(saleOrderService.findById(id));
    }

    @PostMapping("/search")
    public ApiResponse<PageResult<SaleOrderResponseDto>> getSaleOrders(@RequestBody SaleOrderSearchRequestDto searchDto) {
        return ApiResponse.success(saleOrderService.searchSaleOrders(searchDto));
    }

    @PostMapping
    public ApiResponse<Map<String, Long>> createSaleOrder(@Valid @RequestBody SaleOrderCreateRequestDto createRequestDto,
                                                      @RequestHeader("X-Request-Id") String requestId) {
        return ApiResponse.success(Map.of("saleOrderId", saleOrderService.createSaleOrder(createRequestDto, requestId)));
    }

    @PatchMapping
    public ApiResponse<Void> updateSaleOrder(@RequestBody SaleOrderUpdateRequestDto updateRequestDto) {
        saleOrderService.updateSaleOrder(updateRequestDto);
        return ApiResponse.success();
    }
}
