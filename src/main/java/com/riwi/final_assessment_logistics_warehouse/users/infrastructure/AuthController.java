package com.riwi.final_assessment_logistics_warehouse.users.infrastructure;

import org.springframework.web.bind.annotation.RestController;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response.ProblemDetailWithErrors;
import com.riwi.final_assessment_logistics_warehouse.users.application.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.request.LoginUserDto;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.request.RegisterUserDto;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.response.LoginResponseDataDto;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.response.LoginResponseDto;
import com.riwi.final_assessment_logistics_warehouse.users.infrastructure.dtos.response.RegisterResponseDto;

@Tag(name = "Authentication and Registration")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Register user", description = "Description: Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registration is successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegisterResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "The request body has invalid values",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetailWithErrors.class)) }),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) }), })
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        authService.registerUser(registerUserDto);
        RegisterResponseDto registerResponseDto = RegisterResponseDto.builder().status(HttpStatus.CREATED.value())
                .message("User registered successfully").build();
        return ResponseEntity.status(HttpStatus.CREATED).body(registerResponseDto);
    }

    @Operation(summary = "User authentication", description = "Description: Logs in a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authentication is successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "The request body has invalid values",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetailWithErrors.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))) })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        String token = authService.login(loginUserDto);
        LoginResponseDataDto loginResponseDataDto = new LoginResponseDataDto(token);
        LoginResponseDto loginResponseDto = LoginResponseDto.builder().status(HttpStatus.OK.value())
                .message("User successfully authenticated!").data(loginResponseDataDto).build();
        return ResponseEntity.ok(loginResponseDto);
    }

}
