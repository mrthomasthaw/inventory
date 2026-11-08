package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.repo.FormNoSequenceRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class SaleOrderIntegrationTest {

    @Autowired
    private FormNoSequenceRepo formNoSequenceRepo;


    @BeforeEach
    public void setup() {
        formNoSequenceRepo.save(FormNoSequence.builder()
                        .formType(FormType.SALE_ORDER.getCode())
                        .prefix("SO")
                        .date(LocalDate.now())
                        .lastSequence(0L)
                        .build());
    }

}
