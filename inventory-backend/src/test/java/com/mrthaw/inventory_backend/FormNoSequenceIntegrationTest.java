package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.repo.FormNoSequenceRepo;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
@SpringBootTest
public class FormNoSequenceIntegrationTest {

    @Autowired
    private FormNoSequenceRepo formNoSequenceRepo;

    @Autowired
    private FormNoSequenceService formNoSequenceService;

    @BeforeEach
    public void setup() {
        System.out.println("start setup");
        formNoSequenceRepo.deleteAll();

        formNoSequenceRepo.save(FormNoSequence.builder()
                        .formType(FormType.PURCHASE_ORDER.getCode())
                        .lastSequence(4L)
                        .prefix("PO")
                        .date(LocalDate.now().minusDays(30L))
                .build());

        System.out.println("end setup");
    }

    //@Test
    public void generate_form_no_when_form_no_with_form_type_and_date_found() {
        String formNo = formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode());

        assertThat(formNo).isEqualTo("PO-202610260005");
    }

    @Test
    public void generate_form_no_when_no_form_no_with_form_type_found() {
        String formNo = formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode());

        assertThat(formNo).isEqualTo("PO-202610260001");

        assertThat(formNoSequenceRepo.findAll().size()).isEqualTo(2);
    }

}
