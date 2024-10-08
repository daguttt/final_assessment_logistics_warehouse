package com.riwi.final_assessment_logistics_warehouse.loads.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.riwi.final_assessment_logistics_warehouse.loads.domain.LoadEntity;
import com.riwi.final_assessment_logistics_warehouse.loads.domain.LoadRepository;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletRepository;

@Component
@Order(3)
public class LoadsSeeder implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LoadRepository loadRepository;

    @Autowired
    private PalletRepository palletRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("-***********************************************-");
        logger.info("Seeding loads");

        // Check if there are enough loads in the database
        if (this.loadRepository.count() >= 2) {
            logger.info("There are enough loads in the database");
            logger.info("Skipping seeding loads");
            logger.debug("-***********************************************-");
            return;
        }

        // Get pallet to associate with initial loads
        PalletEntity palletEntity = this.palletRepository.findAll().get(0);
        if (palletEntity == null)
            throw new RuntimeException("Could not find a pallet to relate with initial loads");

        // Create initial loads
        List<LoadEntity> initialLoadEntities = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            LoadEntity loadEntity = LoadEntity.builder().weight(5.0 * i).dimensions("10x10x10").pallet(palletEntity)
                    .build();
            loadEntity.setCreatedBy(palletEntity.getCreatedBy());
            loadEntity.setModifiedBy(palletEntity.getCreatedBy());
            initialLoadEntities.add(loadEntity);
        }
        this.loadRepository.saveAll(initialLoadEntities);
        logger.info("LoadsSeeder runned successfully");
        logger.debug("-***********************************************-");
    }

}
