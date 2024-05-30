package com.breezevillepark_ms.bvp_ms.recipe;

import org.springframework.stereotype.Service;

@Service
public class RecipeMapper {
    public Recipe toRecipe(RecipeRequest request){
        return Recipe.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .totalCost(request.totalCost())
                .canCook(true)
                .build();
    }

    public RecipeResponse toRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .recipeId(recipe.getRecipeId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .price(recipe.getPrice())
                .profit(recipe.getProfit())
                .totalCost(recipe.getTotalCost())
                .serving(recipe.getServing())
                .canCook(recipe.isCanCook())
                .recipeCategory(recipe.getRecipeCategory())
                .groups(recipe.getGroups())
                .recipeMaterials(recipe.getRecipeMaterials())
                .orderItems(recipe.getOrderItems())
                .build();
    }
    public Recipe backtoRecipe(RecipeUpdateRequest updateRequest){
        return Recipe.builder()
                .recipeId(updateRequest.id())
                .title(updateRequest.title())
                .description(updateRequest.description())
                .price(updateRequest.price())
                .totalCost(updateRequest.totalCost())
                .canCook(updateRequest.canCook())
                .build();
    }
}
