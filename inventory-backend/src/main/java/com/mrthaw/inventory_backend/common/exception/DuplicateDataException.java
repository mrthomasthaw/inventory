package com.mrthaw.inventory_backend.common.exception;

public class DuplicateDataException extends RuntimeException {

    public DuplicateDataException(String msg) {
        super(msg);
    }
}
