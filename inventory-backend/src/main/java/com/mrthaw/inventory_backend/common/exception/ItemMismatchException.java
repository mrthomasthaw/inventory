package com.mrthaw.inventory_backend.common.exception;

public class ItemMismatchException extends RuntimeException {

    public ItemMismatchException(String msg) {
        super(msg);
    }
}
