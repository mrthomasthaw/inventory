package com.mrthaw.inventory_backend.common.util;

import com.mrthaw.inventory_backend.goods_received.dto.GoodsReceivedItemRequestDto;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    public static boolean isPositiveInt(Integer code) {
        return code != null && code > 0;
    }

    public static boolean isValidList(List itemList) {
        return  itemList != null && ! itemList.isEmpty();
    }

}
