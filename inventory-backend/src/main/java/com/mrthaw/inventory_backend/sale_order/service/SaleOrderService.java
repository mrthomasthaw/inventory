package com.mrthaw.inventory_backend.sale_order.service;

import com.mrthaw.inventory_backend.common.util.PageResult;
import com.mrthaw.inventory_backend.goods_issued.event.GoodsIssuedSubmitEvent;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderCreateRequestDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderSearchRequestDto;
import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderUpdateRequestDto;
import org.hibernate.LockMode;

public interface SaleOrderService {

    PageResult<SaleOrderResponseDto> searchSaleOrders(SaleOrderSearchRequestDto searchDto);

    Long createSaleOrder(SaleOrderCreateRequestDto createRequestDto, String requestId);

    void updateSaleOrder(SaleOrderUpdateRequestDto updateRequestDto);

    SaleOrderResponseDto findById(Long id);

    SaleOrderResponseDto findBySONo(String formNo, LockMode lockMode);

    void updateSaleOrderOnGoodsIssuedSubmit(GoodsIssuedSubmitEvent event);
}
