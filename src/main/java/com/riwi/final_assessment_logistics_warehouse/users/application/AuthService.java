package com.riwi.final_assessment_logistics_warehouse.users.application;

import java.util.Optional;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.security.JwtUtils;
import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.security.UserDetailsImpl;
import com.riwi.final_assessment_logistics_warehouse.users.domain.Roles;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserEntity;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserRepository;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.request.LoginUserDto;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.request.RegisterUserDto;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void registerUser(RegisterUserDto registerUserDto) {
        // Check if user with the email exists
        Optional<UserEntity> foundUser = userRepository.findByEmail(registerUserDto.getEmail());
        if (foundUser.isPresent())
            throw new HttpClientErrorException(HttpStatus.CONFLICT,
                    String.format("User with email '%s' already exists", registerUserDto.getEmail()));

        String encodedPassword = this.passwordEncoder.encode(registerUserDto.getPassword());
        Roles roleToRegister = Roles.valueOf(registerUserDto.getRole());
        var userEntity = UserEntity.builder().email(registerUserDto.getEmail()).fullname(registerUserDto.getFullname())
                .password(encodedPassword).role(roleToRegister).build();

        userEntity.setCreatedBy(userEntity);
        userEntity.setModifiedBy(userEntity);

        // Create user in the database
        var savedUserEntity = userRepository.save(userEntity);
        String message = String.format("User with email %s registered successfully", savedUserEntity.getEmail());
        logger.info(message);
    }

    public String login(LoginUserDto loginUserDto) {
        // Check if user exists
        UserEntity foundUser = userRepository.findByEmail(loginUserDto.getEmail())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,
                        String.format("User with email '%s' not found", loginUserDto.getEmail())));

        // Check user password
        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));

        // Generate token
        UserDetails userDetails = new UserDetailsImpl(foundUser);
        String token = jwtUtils.generateToken(Map.of("role", foundUser.getRole()), userDetails);
        return token;

    }
}