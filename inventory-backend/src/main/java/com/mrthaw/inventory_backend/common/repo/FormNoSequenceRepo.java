package com.mrthaw.inventory_backend.common.repo;

import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;


import java.time.LocalDate;
import java.util.Optional;

public interface FormNoSequenceRepo extends JpaRepository<FormNoSequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<FormNoSequence> findFirstByFormTypeOrderByCreatedTimeDesc(int formTypeCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<FormNoSequence> findByFormTypeAndDate(int formTypeCode, LocalDate date);

}
