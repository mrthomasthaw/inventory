package com.mrthaw.inventory_backend.common.enum_constant;

import lombok.Getter;

import javax.tools.Diagnostic;

@Getter
public enum ApiResponseCode {

    SUCCESS(91, "success"),
    VALIDATION_FAIL(-31, "invalid input"),
    DUPLICATE_DATA(-24, "duplicate data");

    int code;
    String desc;

    ApiResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
