package com.mrthaw.inventory_backend;

import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import com.mrthaw.inventory_backend.common.enum_constant.FormType;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.repo.FormNoSequenceRepo;
import com.mrthaw.inventory_backend.common.service.FormNoSequenceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FormNoSequenceServiceTest {

    @Mock
    FormNoSequenceRepo  formNoSequenceRepo;

    @InjectMocks
    FormNoSequenceServiceImpl formNoSequenceService;

    @Test
    public void generate_next_form_no_when_previous_form_no_found() {

        FormNoSequence  formNoSequence = FormNoSequence.builder()
                .formType(FormType.PURCHASE_ORDER.getCode())
                .prefix("PO")
                .build();

        when(formNoSequenceRepo.findFirstByFormTypeOrderByCreatedTimeDesc(FormType.PURCHASE_ORDER.getCode()))
                .thenReturn(Optional.of(formNoSequence));

        String formNo = formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode());

        assertThat(formNo).isEqualTo("PO-202610260001");
    }

    @Test
    public void throw_when_no_form_no_has() {

        assertThatThrownBy(() -> formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode()))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    public void generate_next_form_no_when_form_no_found_with_form_type_and_current_date() {

        FormNoSequence formNoSequence = FormNoSequence.builder()
                .formType(FormType.PURCHASE_ORDER.getCode())
                .date(LocalDate.now())
                .prefix("PO")
                .lastSequence(3L)
                .build();

        when(formNoSequenceRepo.findByFormTypeAndDate(FormType.PURCHASE_ORDER.getCode(), LocalDate.now()))
                .thenReturn(Optional.of(formNoSequence));

        String formNo = formNoSequenceService.generateNextFormNo(FormType.PURCHASE_ORDER.getCode());

        assertThat(formNo).isEqualTo("PO-202610260004");
    }
}
