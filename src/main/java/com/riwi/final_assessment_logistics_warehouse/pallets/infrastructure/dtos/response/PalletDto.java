package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response;

import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PaletStates;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PalletDto {
    private Long id;
    private Double maxWeight;
    private Double currentWeight;
    private PaletStates state;
    private String location;
}
