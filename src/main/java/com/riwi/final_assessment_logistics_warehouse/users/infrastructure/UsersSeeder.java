package com.riwi.final_assessment_logistics_warehouse.users.infrastructure;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.riwi.final_assessment_logistics_warehouse.users.domain.Roles;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserEntity;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserRepository;

@Component
@Order(1)
public class UsersSeeder implements ApplicationRunner {

    @Value("${seeders.users.admin-user.email}")
    private String adminUserEmail;

    @Value("${seeders.users.admin-user.password}")
    private String adminUserPassword;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.debug("-***********************************************-");
        logger.info("Seeding users");

        // Check if initial admin user exists
        Optional<UserEntity> foundUser = userRepository.findByEmail(this.adminUserEmail);

        if (foundUser.isPresent()) {
            logger.info("User with email '{}' already exists", foundUser.get().getEmail());
            logger.info("Skipping seeding users");
            logger.debug("-***********************************************-");
            return;
        }

        // Create initial admin user
        var encodedPassword = this.passwordEncoder.encode(adminUserPassword);
        var userEntity = UserEntity.builder().fullname("admin").email(this.adminUserEmail).password(encodedPassword)
                .role(Roles.ADMIN).build();
        userEntity.setCreatedBy(userEntity);
        userEntity.setModifiedBy(userEntity);

        userRepository.save(userEntity);
        logger.info("UserSeeder runned successfully");
        logger.debug("-***********************************************-");

    }

}
