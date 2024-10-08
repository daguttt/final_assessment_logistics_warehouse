package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.validators.enums.ValidEnum;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletStates;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePalletDto extends CreatePalletDto {
    @Schema(example = "AVAILABLE", description = "The state of the pallet")
    @ValidEnum(enumClass = PalletStates.class,
            message = "The state must be one of the following: 'AVAILABLE', 'IN_USE', 'BROKEN'")
    @NotBlank(message = "The state cannot be blank")
    private String state;

}
