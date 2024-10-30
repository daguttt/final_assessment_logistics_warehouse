package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.exception_handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DtoValidationsExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationExceptions(MethodArgumentNotValidException exception) {

        ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(exception.getStatusCode(),
                "The request body data may not have valid values");

        problemDetails.setProperty("errors", exception.getAllErrors().stream().map(err -> {
            return Map.ofEntries(Map.entry("field", ((FieldError) err).getField()),
                    Map.entry("error", err.getDefaultMessage()));
        }).toArray());

        return problemDetails;
    }

    /**
     * This exception handler catches when there's an error in the request body
     * because of its JSON format is not valid. (e.g, a trailing comma)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
