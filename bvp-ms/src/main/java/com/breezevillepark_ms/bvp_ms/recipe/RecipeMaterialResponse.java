package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.material.Material;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeMaterialResponse {
    private String comment;
    private BigDecimal cost;
    private Double quantity;
    private Recipe recipe;
    private Material material;
}
