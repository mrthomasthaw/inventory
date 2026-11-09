package com.mrthaw.inventory_backend.sale_order.api;


import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import org.hibernate.LockMode;

public interface SaleOrderApi {

    SaleOrderResponseDto findBySONo(String formNo, LockMode lockMode);

    SaleOrderResponseDto findById(Long id);
}
