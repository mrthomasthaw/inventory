package com.mrthaw.inventory_backend.common.service;

import com.mrthaw.inventory_backend.common.entity.FormNoSequence;
import com.mrthaw.inventory_backend.common.exception.DataNotFoundException;
import com.mrthaw.inventory_backend.common.repo.FormNoSequenceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class FormNoSequenceServiceImpl implements FormNoSequenceService {

    private final FormNoSequenceRepo formNoSequenceRepo;

    @Override
    public String generateNextFormNo(int formTypeCode) {

        LocalDate currentDate = LocalDate.now();

        FormNoSequence formNoSequence = formNoSequenceRepo.findByFormTypeAndDate(formTypeCode, currentDate)
                .orElseGet(() -> {
                    FormNoSequence previousFormNoSeq = formNoSequenceRepo.findFirstByFormTypeOrderByCreatedTimeDesc(formTypeCode)
                            .orElseThrow(() -> new DataNotFoundException("No data found for previous form no, please create a new one"));

                    String prefix = previousFormNoSeq.getPrefix();

                    return FormNoSequence.builder()
                            .prefix(prefix)
                            .date(currentDate)
                            .formType(formTypeCode)
                            .lastSequence(0L)
                            .build();
                });

        Long lastSequence = formNoSequence.getLastSequence() + 1;

        formNoSequence.setLastSequence(lastSequence);

        formNoSequenceRepo.save(formNoSequence);

        LocalDate date = formNoSequence.getDate();

        String formatPattern = formNoSequence.getPrefix().concat("-") + date.toString().replace("-", "") + "%04d";

        return String.format(formatPattern, lastSequence);
    }
}
