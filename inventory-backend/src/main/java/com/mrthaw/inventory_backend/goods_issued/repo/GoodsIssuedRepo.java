package com.mrthaw.inventory_backend.goods_issued.repo;

import com.mrthaw.inventory_backend.goods_issued.entity.GoodsIssued;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GoodsIssuedRepo extends JpaRepository<GoodsIssued, Long>, JpaSpecificationExecutor<GoodsIssued> {

}
