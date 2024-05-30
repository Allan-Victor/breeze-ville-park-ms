package com.breezevillepark_ms.bvp_ms.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Column(updatable = false,
            nullable = false)
    private LocalDateTime submittedAt;

    @Column(insertable = false)
    private LocalDateTime lastModifiedAt;

    private String createdBy;
    private String lastModifiedBy;
}
