package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.material.Material;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class RecipeMaterial {
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
