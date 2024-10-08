package com.riwi.final_assessment_logistics_warehouse.pallets.domain;

import java.util.Set;

import com.riwi.final_assessment_logistics_warehouse.common.domain.AuditableEntity;
import com.riwi.final_assessment_logistics_warehouse.loads.domain.LoadEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Entity(name = "pallets")
public class PalletEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double maxWeight;

    @Column(nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    @Builder.Default
    private Double currentWeight = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PalletStates state = PalletStates.AVAILABLE;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "pallet")
    private Set<LoadEntity> loads;
}
