package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ApiResponseDto<TData> {
    private Integer status;
    private String message;
    private TData data;
}
