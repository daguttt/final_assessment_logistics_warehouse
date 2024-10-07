package com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ApiResponseDto<TData> {
    private Integer status;
    private String message;
    private TData data;
}
