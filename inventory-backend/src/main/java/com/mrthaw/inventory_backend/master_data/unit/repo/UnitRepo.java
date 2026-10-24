package com.mrthaw.inventory_backend.master_data.unit.repo;

import com.mrthaw.inventory_backend.master_data.unit.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepo extends JpaRepository<Unit,Long> {
}
