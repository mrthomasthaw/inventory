package com.mrthaw.inventory_backend.purchase_order.repo;

import com.mrthaw.inventory_backend.purchase_order.entity.PurchaseOrder;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {

    @Override
    @EntityGraph(attributePaths = "supplier")
    Page<PurchaseOrder> findAll(Specification<PurchaseOrder> spec, Pageable pageable);


    @Override
    @EntityGraph(attributePaths = "purchaseOrderItems")
    Optional<PurchaseOrder> findById(Long purchaseOrderId);

    //@EntityGraph(attributePaths = "purchaseOrderItems")
    @Query(value = "SELECT p FROM PurchaseOrder p JOIN FETCH p.purchaseOrderItems WHERE p.id = :purchaseOrderId")
    Optional<PurchaseOrder> findByIdForUpdate(@Param("purchaseOrderId") Long purchaseOrderId, LockModeType lockModeType);
}
