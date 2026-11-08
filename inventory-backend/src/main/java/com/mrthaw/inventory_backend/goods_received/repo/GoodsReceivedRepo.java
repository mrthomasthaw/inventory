package com.mrthaw.inventory_backend.goods_received.repo;

import com.mrthaw.inventory_backend.goods_received.entity.GoodsReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoodsReceivedRepo extends JpaRepository<GoodsReceived, Long>, JpaSpecificationExecutor<GoodsReceived> {

}
