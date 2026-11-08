package com.mrthaw.inventory_backend.common.exception;

public class ItemQtyExceedException extends RuntimeException {

    public ItemQtyExceedException(String msg) {
        super(msg);
    }
}
