package com.mrthaw.inventory_backend.master_data.main_category.repo;

import com.mrthaw.inventory_backend.master_data.main_category.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainCategoryRepo extends JpaRepository<MainCategory,Long> {
}
