package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.material.Material;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;

public record RecipeMaterialRequest(
        @NotNull(message = "Comment cannot be null")
        @NotEmpty(message = "Comment cannot be empty")
        String comment,

        @NotNull(message = "Cost cannot be null")
        @NotEmpty(message = "Cost cannot be empty")
        BigDecimal cost,

        @NotNull(message = "Quantity cannot be null")
        @NotEmpty(message = "Quantity cannot be empty")
        Double quantity,
        Recipe recipe,
        Material material

) {
}
