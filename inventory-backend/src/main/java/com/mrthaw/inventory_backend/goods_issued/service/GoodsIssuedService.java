package com.mrthaw.inventory_backend.goods_issued.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedCreateRequestDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedResponseDto;
import com.mrthaw.inventory_backend.goods_issued.dto.GoodsIssuedSearchRequestDto;

public interface GoodsIssuedService {

    Long createGoodsIssued(GoodsIssuedCreateRequestDto createRequestDto, String requestId);

    PageResult<GoodsIssuedResponseDto> searchGoodsIssueds(GoodsIssuedSearchRequestDto searchDto);
}
