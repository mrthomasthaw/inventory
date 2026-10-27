package com.mrthaw.inventory_backend.master_data.item.repo;

import com.mrthaw.inventory_backend.master_data.item.entity.Item;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepo extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    @EntityGraph(attributePaths = {"mainCategory", "unit"})
    @Override
    Page<Item> findAll(Specification<Item> spec, Pageable pageable);

    boolean existsByItemCode(@NotEmpty(message = "Item code is required") String itemCode);

    boolean existsByItemCodeAndIdNot(String itemCode, @NotNull(message = "Id is required") Long id);
}
