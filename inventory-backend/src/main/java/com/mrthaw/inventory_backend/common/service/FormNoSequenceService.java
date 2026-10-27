package com.mrthaw.inventory_backend.common.service;

import com.mrthaw.inventory_backend.common.enum_constant.FormType;

public interface FormNoSequenceService {

    String generateNextFormNo(int code);
}
