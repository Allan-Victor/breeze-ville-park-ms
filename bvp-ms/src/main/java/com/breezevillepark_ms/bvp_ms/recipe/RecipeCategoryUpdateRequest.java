package com.breezevillepark_ms.bvp_ms.recipe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecipeCategoryUpdateRequest(
        Integer id,

        @NotNull @NotEmpty String name
) {
}
