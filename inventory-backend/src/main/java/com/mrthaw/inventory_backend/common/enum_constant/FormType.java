package com.mrthaw.inventory_backend.common.enum_constant;


import lombok.Getter;

import javax.tools.Diagnostic;

@Getter
public enum FormType {

    PURCHASE_ORDER(1, "Purchase Order"),
    GOODS_RECEIVED(2, "Goods Received"),
    SALE_ORDER(3, "Sale Order"),
    GOODS_ISSUED(4, "Goods Issued");
    int code;

    String desc;

    FormType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
