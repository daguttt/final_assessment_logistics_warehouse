package com.riwi.final_assessment_logistics_warehouse.common.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.riwi.final_assessment_logistics_warehouse.users.domain.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditableEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
    @Builder.Default
    private LocalDateTime modifiedAt = LocalDateTime.now();

    @CreatedBy
    @OneToOne
    private UserEntity createdBy;

    @LastModifiedBy
    @OneToOne
    private UserEntity modifiedBy;
}