package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.exception_handlers;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class HttpExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail handleHttpExceptions(HttpClientErrorException exception) {
        return ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getStatusText());
    }
}
