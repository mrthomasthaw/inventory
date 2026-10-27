package com.mrthaw.inventory_backend.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mrthaw.inventory_backend.common.enum_constant.ApiResponseCode;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;
    private List<String> errorList;

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int code, String message, List<String> errorList) {
        this.code = code;
        this.message = message;
        this.errorList = errorList;
    }


    public static <T> ApiResponse <T> success() {
        return new ApiResponse<>(ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getDesc(), null);
    }

    public static <T> ApiResponse <T> success(T data) {
        return new ApiResponse<>(ApiResponseCode.SUCCESS.getCode(), ApiResponseCode.SUCCESS.getDesc(), data);
    }

    public static ApiResponse<List<String>> validationError(List<String> errorList) {
        return new ApiResponse<>(ApiResponseCode.VALIDATION_FAIL.getCode(), ApiResponseCode.VALIDATION_FAIL.getDesc(), errorList);
    }

    public static ApiResponse<String> duplicateDataError() {
        return new ApiResponse<>(ApiResponseCode.DUPLICATE_DATA.getCode(), ApiResponseCode.DUPLICATE_DATA.getDesc(), null);
    }

    public static ApiResponse<Void> internalServerError() {
        return new ApiResponse<>(ApiResponseCode.INTERNAL_SERVER_ERROR.getCode(), ApiResponseCode.INTERNAL_SERVER_ERROR.getDesc(), null);
    }

    public static ApiResponse<String> invalidFormStatusError() {
        return new ApiResponse<>(ApiResponseCode.INVALID_FORM_STATUS.getCode(), ApiResponseCode.INVALID_FORM_STATUS.getDesc(), null);
    }
}
