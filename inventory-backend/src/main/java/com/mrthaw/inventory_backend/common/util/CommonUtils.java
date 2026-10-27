package com.mrthaw.inventory_backend.common.util;

import org.jspecify.annotations.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;

public class CommonUtils {

    public static boolean isValidString(String val) {
        return val != null && ! val.isBlank();
    }

    public static @NonNull List<String> convertToErrorList(MethodArgumentNotValidException er) {
        return er.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

}
