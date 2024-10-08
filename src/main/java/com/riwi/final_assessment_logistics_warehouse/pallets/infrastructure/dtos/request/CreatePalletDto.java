package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request;

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
public class CreatePalletDto {
    @Schema(example = "15.0", description = "The max weight of the pallet")
    @Min(value = 5, message = "The max weight must be greater than or equal to 5")
    private Double maxWeight;

    @Schema(example = "Warehouse A", description = "The location of the pallet")
    @NotBlank(message = "The location cannot be blank")
    private String location;
}
