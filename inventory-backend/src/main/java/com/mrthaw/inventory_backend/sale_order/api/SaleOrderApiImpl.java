package com.mrthaw.inventory_backend.sale_order.api;


import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import com.mrthaw.inventory_backend.sale_order.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.hibernate.LockMode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaleOrderApiImpl implements SaleOrderApi {

    private final SaleOrderService saleOrderService;


    @Override
    public SaleOrderResponseDto findBySONo(String formNo, LockMode lockMode) {
        return saleOrderService.findBySONo(formNo, lockMode);
    }

    @Override
    public SaleOrderResponseDto findById(Long id) {
        return saleOrderService.findById(id);
    }
}
