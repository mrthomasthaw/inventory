package com.mrthaw.inventory_backend.common.enum_constant;

import lombok.Getter;

import javax.tools.Diagnostic;

@Getter
public enum ApiResponseCode {

    SUCCESS(91, "success"),
    VALIDATION_FAIL(-31, "invalid input"),
    DUPLICATE_DATA(-24, "duplicate data"),
    INTERNAL_SERVER_ERROR(-15, "internal server error"),
    INVALID_FORM_STATUS(-49, "invalid form status");

    int code;
    String desc;

    ApiResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
