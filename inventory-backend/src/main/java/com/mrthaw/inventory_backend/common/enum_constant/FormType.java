package com.mrthaw.inventory_backend.common.enum_constant;


import lombok.Getter;

@Getter
public enum FormType {

    PURCHASE_ORDER(1, "Purchase Order");

    int code;

    String desc;

    FormType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
