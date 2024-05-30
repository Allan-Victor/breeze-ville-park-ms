package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.material.Material;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
public class RecipeMaterial extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "recipe_material_seq")
    @SequenceGenerator(name = "recipe_material_seq",
            sequenceName = "recipe_material_seq",
            allocationSize = 1)
    private Integer recipeMaterialId;
    private String comment;
    private BigDecimal cost;

    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

}
