package com.riwi.final_assessment_logistics_warehouse.pallets.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletRepository;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.CreatePalletDto;

@Service
public class PalletCreator {
    @Autowired
    private PalletRepository palletRepository;

    public PalletEntity create(CreatePalletDto createPalletDto) {
        PalletEntity palletEntity = PalletEntity.builder().maxWeight(createPalletDto.getMaxWeight())
                .location(createPalletDto.getLocation()).build();
        return this.palletRepository.save(palletEntity);

    }
}
