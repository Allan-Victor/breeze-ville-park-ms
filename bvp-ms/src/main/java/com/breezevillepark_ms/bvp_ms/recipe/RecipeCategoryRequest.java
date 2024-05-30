package com.breezevillepark_ms.bvp_ms.recipe;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecipeCategoryRequest(
        @NotNull(message = "Category name cannot be null")
        @NotEmpty(message = "Category Name cannot be empty")
        String categoryName
) {
}
