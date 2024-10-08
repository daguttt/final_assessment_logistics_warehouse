package com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response.ProblemDetailWithErrors;
import com.riwi.final_assessment_logistics_warehouse.pallets.application.PalletsService;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.CreatePalletDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.request.UpdatePalletDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.CreatedPalletResponseDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.DeletedPalletResponseDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.PalletDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.PalletsResponseDto;
import com.riwi.final_assessment_logistics_warehouse.pallets.infrastructure.dtos.response.SinglePalletResponseDto;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pallets management")
@RestController
@RequestMapping("/pallets")
public class PalletsController {

    @Autowired
    private PalletsService palletsService;

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
    public ResponseEntity<CreatedPalletResponseDto> createPallet(@Valid @RequestBody CreatePalletDto createPalletDto) {
        PalletEntity createdPalletEntity = this.palletsService.create(createPalletDto);
        PalletDto palletDto = palletEntityToPalletDto(createdPalletEntity);

        var httpStatus = HttpStatus.CREATED.value();
        CreatedPalletResponseDto createdPalletResponseDto = CreatedPalletResponseDto.builder().status(httpStatus)
                .message("Pallet created successfully").data(palletDto).build();
        return ResponseEntity.status(httpStatus).body(createdPalletResponseDto);
    }

    @Operation(summary = "Get all pallets")
    @GetMapping
    ResponseEntity<PalletsResponseDto> getAllPallets() {
        var palletEntities = this.palletsService.getAllPallets();
        var palletDtos = palletEntities.stream().map(this::palletEntityToPalletDto).toArray(PalletDto[]::new);

        var httpStatus = HttpStatus.OK.value();
        PalletsResponseDto palletsResponseDto = PalletsResponseDto.builder().status(httpStatus)
                .message("Pallets fetched successfully").data(palletDtos).build();

        return ResponseEntity.status(httpStatus).body(palletsResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SinglePalletResponseDto> getPalletById(@PathVariable Long id) {
        var palletEntity = this.palletsService.getPalletById(id);
        var palletDto = palletEntityToPalletDto(palletEntity);

        var httpStatus = HttpStatus.OK.value();
        SinglePalletResponseDto singlePalletResponseDto = SinglePalletResponseDto.builder().status(httpStatus)
                .message("Pallet fetched successfully").data(palletDto).build();
        return ResponseEntity.status(httpStatus).body(singlePalletResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatedPalletResponseDto> updatePallet(@PathVariable Long id,
            @Valid @RequestBody UpdatePalletDto updatePalletDto) {
        PalletEntity updatedPalletEntity = this.palletsService.update(id, updatePalletDto);
        PalletDto palletDto = palletEntityToPalletDto(updatedPalletEntity);

        var httpStatus = HttpStatus.OK.value();
        CreatedPalletResponseDto createdPalletResponseDto = CreatedPalletResponseDto.builder().status(httpStatus)
                .message("Pallet updated successfully").data(palletDto).build();
        return ResponseEntity.status(httpStatus).body(createdPalletResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeletedPalletResponseDto> deletePalletById(@PathVariable Long id) {
        this.palletsService.deletePalletById(id);
        var httpStatus = HttpStatus.OK.value();
        DeletedPalletResponseDto createdPalletResponseDto = DeletedPalletResponseDto.builder().status(httpStatus)
                .message("Pallet deleted successfully").build();
        return ResponseEntity.status(httpStatus).body(createdPalletResponseDto);
    }

    private PalletDto palletEntityToPalletDto(PalletEntity createdPalletEntity) {
        PalletDto palletDto = new PalletDto();
        palletDto.setId(createdPalletEntity.getId());
        palletDto.setMaxWeight(createdPalletEntity.getMaxWeight());
        palletDto.setCurrentWeight(createdPalletEntity.getCurrentWeight());
        palletDto.setState(createdPalletEntity.getState());
        palletDto.setLocation(createdPalletEntity.getLocation());
        return palletDto;
    }
}
