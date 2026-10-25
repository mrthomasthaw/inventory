package com.mrthaw.inventory_backend.master_data.item.repo;

import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepo extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    boolean existsByItemCode(@NotEmpty(message = "Item code is required") String itemCode);

    boolean existsByItemCodeAndIdNot(String itemCode, @NotNull(message = "Id is required") Long id);
}
