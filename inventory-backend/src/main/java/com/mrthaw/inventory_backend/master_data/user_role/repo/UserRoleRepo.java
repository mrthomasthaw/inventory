package com.mrthaw.inventory_backend.master_data.user_role.repo;

import com.mrthaw.inventory_backend.master_data.user_role.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRoleRepo extends JpaRepository<UserRole, Long> {
}
