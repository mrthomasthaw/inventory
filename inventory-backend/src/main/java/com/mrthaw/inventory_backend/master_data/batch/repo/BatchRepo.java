package com.mrthaw.inventory_backend.master_data.batch.repo;

import com.mrthaw.inventory_backend.master_data.batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepo extends JpaRepository<Batch,Long> {
}
