package com.mrthaw.inventory_backend.common.enum_constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum RecordStatus {

    ACTIVE(1, "Active"), INACTIVE(2, "Inactive");

    int code;
    String desc;

    RecordStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(Integer status) {
        return getByCode(status)
                .map(RecordStatus::getDesc)
                .orElse(null);
    }

    public static Optional<RecordStatus> getByCode(Integer status) {
        if(status == null) return Optional.empty();

        return Arrays.stream(RecordStatus.values())
                .filter(recordStatus -> status.equals(recordStatus.getCode()))
                .findFirst();
    }
}
