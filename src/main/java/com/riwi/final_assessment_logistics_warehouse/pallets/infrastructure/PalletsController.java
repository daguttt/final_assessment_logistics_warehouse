package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response.ProblemDetailWithErrors;
import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.security.annotations.CurrentUser;
import com.riwi.final_assessment_logistics_warehouse.pallets.application.PalletCreator;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.CreatePalletDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.CreatedPalletResponseDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.PalletDto;
import com.riwi.final_assessment_logistics_warehouse.users.domain.UserEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pallets management")
@RestController
@RequestMapping("/pallets")
public class PalletsController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PalletCreator palletCreator;

    public PalletsController(PalletCreator palletCreator) {
        this.palletCreator = palletCreator;
    }

    @Operation(summary = "Create a new pallet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pallet created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatedPalletResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "The request body has invalid values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetailWithErrors.class))),
            @ApiResponse(responseCode = "403", description = "User does not have permission to create a pallet",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))) })
    @PostMapping
    public ResponseEntity<CreatedPalletResponseDto> createPallet(@Valid @RequestBody CreatePalletDto createPalletDto,
            @CurrentUser UserEntity authUserEntity) {
        this.logger.debug("User authenticated: {}", authUserEntity);

        PalletEntity createdPalletEntity = palletCreator.create(createPalletDto);
        PalletDto palletDto = new PalletDto();
        palletDto.setId(createdPalletEntity.getId());
        palletDto.setMaxWeight(createdPalletEntity.getMaxWeight());
        palletDto.setCurrentWeight(createdPalletEntity.getCurrentWeight());
        palletDto.setState(createdPalletEntity.getState());
        palletDto.setLocation(createdPalletEntity.getLocation());

        var httpStatus = HttpStatus.CREATED.value();
        CreatedPalletResponseDto createdPalletResponseDto = CreatedPalletResponseDto.builder().status(httpStatus)
                .message("Pallet created successfully").data(palletDto).build();
        return ResponseEntity.status(httpStatus).body(createdPalletResponseDto);
    }

}
