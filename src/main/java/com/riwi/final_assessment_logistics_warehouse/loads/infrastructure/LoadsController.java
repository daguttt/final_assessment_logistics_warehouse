package com.riwi.final_assessment_logistics_warehouse.loads.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.riwi.final_assessment_logistics_warehouse.common.infrastructure.dtos.response.NoDataResponseDto;
import com.riwi.final_assessment_logistics_warehouse.loads.infrastructure.dtos.request.CreateLoadDto;
import com.riwi.final_assessment_logistics_warehouse.loads.infrastructure.dtos.response.LoadsResponseDto;
import com.riwi.final_assessment_logistics_warehouse.loads.infrastructure.dtos.response.SingleLoadResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Loads management")
@RestController
@RequestMapping("/loads")
public class LoadsController {

    @Operation(summary = "Create load")
    @PostMapping
    public ResponseEntity<SingleLoadResponseDto> createLoad(@Valid @RequestBody CreateLoadDto createLoadDto) {
        return null;
    }

    @Operation(summary = "Get all loads")
    @GetMapping
    public ResponseEntity<LoadsResponseDto> getAllLoads() {
        return null;
    }

    @Operation(summary = "Get a load by id")
    @GetMapping("/{id}")
    public ResponseEntity<SingleLoadResponseDto> getLoadById(@PathVariable Long id) {
        return null;
    }

    @Operation(summary = "Update a load by id")
    @PostMapping("/{id}")
    public ResponseEntity<SingleLoadResponseDto> updateLoadById(@PathVariable Long id,
            @Valid @RequestBody CreateLoadDto createLoadDto) {
        return null;
    }

    @Operation(summary = "Delete a load by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<NoDataResponseDto> deleteLoadById(@PathVariable Long id) {
        return null;
    }
}
