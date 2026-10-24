package com.mrthaw.inventory_backend.master_data.business_partner.repo;

import com.mrthaw.inventory_backend.master_data.business_partner.entity.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BusinessPartnerRepo extends JpaRepository<BusinessPartner, Long>, JpaSpecificationExecutor<BusinessPartner> {
}
