package com.riwi.final_assessment_logistics_warehouse.pallets.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletRepository;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletStates;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.CreatePalletDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.UpdatePalletDto;

@Service
public class PalletsService {
    @Autowired
    private PalletRepository palletRepository;

    public List<PalletEntity> getAllPallets() {
        return this.palletRepository.findAll();
    }

    public PalletEntity getPalletById(Long id) {
        return this.findPalletById(id);
    }

    public PalletEntity create(CreatePalletDto createPalletDto) {
        PalletEntity palletEntity = PalletEntity.builder().maxWeight(createPalletDto.getMaxWeight())
                .location(createPalletDto.getLocation()).build();
        return this.palletRepository.save(palletEntity);
    }

    public PalletEntity update(Long id, UpdatePalletDto updatePalletDto) {
        PalletEntity palletEntity = this.findPalletById(id);
        palletEntity.setMaxWeight(updatePalletDto.getMaxWeight());
        palletEntity.setLocation(updatePalletDto.getLocation());
        PalletStates newPalletState = PalletStates.valueOf(updatePalletDto.getState());
        palletEntity.setState(newPalletState);
        return this.palletRepository.save(palletEntity);
    }

    public void deletePalletById(Long id) {
        PalletEntity palletEntity = this.findPalletById(id);
        this.palletRepository.delete(palletEntity);
    }

    private PalletEntity findPalletById(Long id) {
        return this.palletRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                String.format("Pallet with id '%s' not found", id)));
    }
}
