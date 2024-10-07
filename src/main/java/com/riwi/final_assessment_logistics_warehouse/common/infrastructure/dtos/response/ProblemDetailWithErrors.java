package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response;

import org.springframework.http.ProblemDetail;

import lombok.Getter;

@Getter
public class ProblemDetailWithErrors extends ProblemDetail {
    private ProblemDetailFieldError[] errors;

    public ProblemDetailWithErrors(int status) {
        super(status);
    }
}
