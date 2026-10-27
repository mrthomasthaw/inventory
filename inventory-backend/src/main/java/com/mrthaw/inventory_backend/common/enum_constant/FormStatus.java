package com.mrthaw.inventory_backend.common.enum_constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum FormStatus {

    READY_TO_PROCESS(1, "Ready To Process"), // when the form is approved if approval requires
    PROCESSING(2, "Processing"), // when the form is being processed with related documents such as goods receipt for purchase order, goods issued for sale order
    COMPLETE(3, "Complete"),
    DRAFT(21, "Draft");


    int code;

    String desc;

    FormStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Optional<FormStatus> getByCode(int code) {
        return Arrays.stream(FormStatus.values())
                .filter(formStatus -> formStatus.code == code)
                .findFirst();
    }

    public static String getDescByCode(int code) {
        return Arrays.stream(FormStatus.values())
                .filter(formStatus -> formStatus.code == code)
                .map(formStatus -> formStatus.desc)
                .findFirst()
                .orElse(null);
    }
}
