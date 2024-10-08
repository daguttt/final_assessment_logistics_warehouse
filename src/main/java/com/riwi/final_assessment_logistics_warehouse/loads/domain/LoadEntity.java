package com.riwi.final_assessment_logistics_warehouse.loads.domain;

import com.riwi.final_assessment_logistics_warehouse.common.domain.AuditableEntity;
import com.riwi.final_assessment_logistics_warehouse.pallets.domain.PalletEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "loads")
public class LoadEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private String dimensions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private LoadStates state = LoadStates.PENDING;

    @ManyToOne
    @JoinColumn(name = "pallet_id", nullable = false)
    private PalletEntity pallet;
}
