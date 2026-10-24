package com.mrthaw.inventory_backend.common.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String msg) {
        super(msg);
    }
}
