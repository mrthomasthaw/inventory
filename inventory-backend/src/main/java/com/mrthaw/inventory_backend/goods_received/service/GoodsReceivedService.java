package com.mrthaw.inventory_backend.goods_received.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_received.dto.*;
import jakarta.validation.Valid;

public interface GoodsReceivedService {
    
    GoodsReceivedDetailResponseDto findById(Long goodsReceivedId);

    PageResult<GoodsReceivedResponseDto> searchGoodsReceiveds(GoodsReceivedSearchRequestDto searchRequestDto);

    Long createGoodsReceived(@Valid GoodsReceivedCreateRequestDto createRequestDto);

    void updateGoodsReceived(@Valid GoodsReceivedUpdateRequestDto updateRequestDto);
}
