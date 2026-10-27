package com.mrthaw.inventory_backend.common.handler;

import com.mrthaw.inventory_backend.common.exception.DuplicateDataException;
import com.mrthaw.inventory_backend.common.exception.InvalidFormStatusException;
import com.mrthaw.inventory_backend.common.response.ApiResponse;
import com.mrthaw.inventory_backend.common.util.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception er) {
        return ApiResponse.internalServerError();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<List<String>> handleException(MethodArgumentNotValidException er) {
        return ApiResponse.validationError(CommonUtils.convertToErrorList(er));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateDataException.class)
    public ApiResponse<String> handleException(DuplicateDataException er) {
        return ApiResponse.duplicateDataError();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormStatusException.class)
    public ApiResponse<String> handleException(InvalidFormStatusException er) {
        return ApiResponse.invalidFormStatusError();
    }

}
