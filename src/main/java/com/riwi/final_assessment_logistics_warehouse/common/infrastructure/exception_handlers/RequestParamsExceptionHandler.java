package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.exception_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class RequestParamsExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleInvalidRequestParams(MethodArgumentTypeMismatchException exception) {
        @SuppressWarnings("null")
        String detail = String.format("Cannot convert value '%s' to type of '%s' for '%s' request parameter.",
                exception.getValue(), exception.getRequiredType().getSimpleName(), exception.getName());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail);
    }
}