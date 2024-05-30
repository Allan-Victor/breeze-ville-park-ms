package com.breezevillepark_ms.bvp_ms.recipe;

import org.springframework.stereotype.Component;

@Component
public class RecipeCategoryMapper {
    public RecipeCategory toRecipeCategory(RecipeCategoryRequest categoryRequest){
        return RecipeCategory.builder()
                .recipeCategoryName(categoryRequest.categoryName())
                .build();
    }

    public RecipeCategoryResponse toCategoryResponse(RecipeCategory category) {
        return RecipeCategoryResponse.builder()
                .recipeCategoryId(category.getRecipeCategoryId())
                .recipeCategoryName(category.getRecipeCategoryName())
                .recipes(category.getRecipes())
                .build();
    }
    public RecipeCategory backToRecipeCategory(RecipeCategoryUpdateRequest updateRequest){
        return RecipeCategory.builder()
                .recipeCategoryId(updateRequest.id())
                .recipeCategoryName(updateRequest.name())
                .build();
    }

}
