package com.mrthaw.inventory_backend.sale_order.repo;

import com.mrthaw.inventory_backend.sale_order.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SaleOrderRepo extends JpaRepository<SaleOrder, Long>, JpaSpecificationExecutor<SaleOrder> {
}
