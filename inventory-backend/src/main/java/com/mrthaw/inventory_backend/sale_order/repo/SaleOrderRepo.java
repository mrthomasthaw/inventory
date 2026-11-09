package com.mrthaw.inventory_backend.sale_order.repo;

import com.mrthaw.inventory_backend.sale_order.dto.SaleOrderResponseDto;
import com.mrthaw.inventory_backend.sale_order.entity.SaleOrder;
import org.hibernate.LockMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SaleOrderRepo extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {

    Optional<SaleOrder> findBySaleOrderNo(String formNo, LockMode lockMode);
}
