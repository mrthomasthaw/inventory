package com.mrthaw.inventory_backend.goods_issued.controller;

import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedCreateRequestDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedResponseDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedSearchRequestDto;
import com.mrthaw.inventory_backend.goods_issued.service.GoodsIssuedService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/goods-issueds")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class GoodsIssuedController {

    private final GoodsIssuedService goodsIssuedService;


    @PostMapping("/search")
    public ApiResponse<PageResult<GoodsIssuedResponseDto>> searchGoodsIssueds(@RequestBody GoodsIssuedSearchRequestDto searchDto) {
        return ApiResponse.success(goodsIssuedService.searchGoodsIssueds(searchDto));
    }

    @PostMapping
    public ApiResponse<Map<String, Object>> createGoodsIssued(@Valid @RequestBody GoodsIssuedCreateRequestDto createRequestDto,
                                                              @RequestHeader("X-Request-ID") String requestId) {
        return ApiResponse.success(Map.of("goodsIssuedId", goodsIssuedService.createGoodsIssued(createRequestDto, requestId)));
    }
}
