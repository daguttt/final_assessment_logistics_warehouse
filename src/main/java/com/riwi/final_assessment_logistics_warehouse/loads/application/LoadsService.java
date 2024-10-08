package com.riwi.final_assessment_logistics_warehouse.loads.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.riwi.final_assessment_logistics_warehouse.loads.domain.LoadEntity;
import com.riwi.final_assessment_logistics_warehouse.loads.domain.LoadRepository;
import com.riwi.final_assessment_logistics_warehouse.loads.infrastructure.dtos.request.CreateLoadDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletRepository;

@Service
public class LoadsService {

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private PalletRepository palletRepository;

    public List<LoadEntity> getAllLoads() {
        return loadRepository.findAll();
    }

    public LoadEntity getLoadById(Long id) {
        return this.findLoadById(id);
    }

    public LoadEntity createLoad(CreateLoadDto createLoadDto) {
        // Find pallet to assign the created load
        PalletEntity palletEntity = this.palletRepository.findById(createLoadDto.getPalletId())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        String.format("Pallet with id '%s' not found", createLoadDto.getPalletId())));

        LoadEntity loadEntity = new LoadEntity();
        loadEntity.setWeight(createLoadDto.getWeight());
        loadEntity.setDimensions(createLoadDto.getDimensions());
        loadEntity.setPallet(palletEntity);
        return this.loadRepository.save(loadEntity);
    }

    private LoadEntity findLoadById(Long id) {
        return this.loadRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                String.format("Load with id '%s' not found", id)));
    }
}
