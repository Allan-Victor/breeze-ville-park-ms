package com.breezevillepark_ms.bvp_ms.recipe;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RecipeRequest(

        @NotNull(message = "100")
        @NotEmpty(message = "100")
        String title,

        @NotNull(message = "101")
        @NotEmpty(message = "101")
        String description,

        @NotNull(message = "102")
        @NotEmpty(message = "102")
        BigDecimal price,

        @NotNull(message = "103")
        @NotEmpty(message = "103")
        BigDecimal totalCost,
        boolean canCook
) {
}
