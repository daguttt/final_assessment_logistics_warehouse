package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PaletStates;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletRepository;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserEntity;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserRepository;

@Component
@Order(2)
public class PalletsSeeder implements ApplicationRunner {
    @Value("${spring.mail.username}")
    private String mailUsername;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PalletRepository paletRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("-***********************************************-");
        logger.info("Seeding palets");

        // Check if there are enough palets in the database
        if (this.paletRepository.count() >= 5) {
            logger.info("There are enough palets in the database");
            logger.info("Skipping seeding palets");
            logger.debug("-***********************************************-");
            return;
        }

        // Get user admin to set audit for palets
        UserEntity admin = this.userRepository.findByEmail(this.mailUsername)
                .orElseThrow(() -> new RuntimeException("Admin user not found"));

        // Create initial palets
        for (int i = 1; i <= 5; i++) {
            PalletEntity paletEntity = PalletEntity.builder().maxWeight(10.0 * i).currentWeight(0.0)
                    .state(PaletStates.AVAILABLE).location("warehouse").build();
            paletEntity.setCreatedBy(admin);
            paletEntity.setModifiedBy(admin);
            this.paletRepository.save(paletEntity);
            logger.info("Palet with id '{}' created", paletEntity.getId());
        }

        logger.debug("-***********************************************-");
    }

}