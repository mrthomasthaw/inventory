package com.mrthaw.inventory_backend.master_data.user_account.repo;

import com.mrthaw.inventory_backend.master_data.user_account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccount, Long>, JpaSpecificationExecutor<UserAccount> {
}
