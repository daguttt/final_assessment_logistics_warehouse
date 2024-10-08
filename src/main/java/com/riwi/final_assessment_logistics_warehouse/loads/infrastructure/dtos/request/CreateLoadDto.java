package com.riwi.final_assessment_logistics_warehouse.loads.infrastructure.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLoadDto {

    @Schema(example = "5.0", description = "The weight of the load")
    @Min(value = 1, message = "Weight cannot be negative")
    private Double weight;

    @Schema(example = "10x10x10", description = "The dimensions of the load")
    @NotBlank(message = "Dimensions cannot be blank")
    private String dimensions;

    @Schema(example = "1", description = "The id of the pallet to assigned the created load")
    @Min(value = 1, message = "Pallet id cannot be negative")
    @NotBlank(message = "Pallet id cannot be blank")
    private Long palletId;

}
