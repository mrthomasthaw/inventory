package com.mrthaw.inventory_backend.goods_received.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_received.dto.*;
import com.mrthaw.inventory_backend.goods_received.service.GoodsReceivedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goods-receiveds")
@CrossOrigin(origins = "http://localhost:8080")
public class GoodsReceivedController {

    private final GoodsReceivedService goodsReceivedService;

    @GetMapping("/{id}")
    public ApiResponse<GoodsReceivedDetailResponseDto> getGoodsReceived(@PathVariable("id") Long goodsReceivedId) {
        return ApiResponse.success(goodsReceivedService.findById(goodsReceivedId));
    }

    @PostMapping("/search")
    public ApiResponse<PageResult<GoodsReceivedResponseDto>> searchGoodsReceiveds(@RequestBody GoodsReceivedSearchRequestDto searchRequestDto) {
        return ApiResponse.success(goodsReceivedService.searchGoodsReceiveds(searchRequestDto));
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createGoodsReceived(@Valid @RequestBody GoodsReceivedCreateRequestDto createRequestDto) {
        return ApiResponse.success(Map.of("goodsReceivedId", goodsReceivedService.createGoodsReceived(createRequestDto)));
    }

    @PatchMapping
    public ApiResponse<Void> updateGoodsReceived(@Valid @RequestBody GoodsReceivedUpdateRequestDto updateRequestDto) {
        goodsReceivedService.updateGoodsReceived(updateRequestDto);
        return ApiResponse.success();
    }
}
