package com.breezevillepark_ms.bvp_ms.recipe;

import org.springframework.stereotype.Component;

@Component
public class RecipeMaterialMapper {
    public RecipeMaterial toRecipeMaterial(RecipeMaterialRequest recipeMaterialRequest){
        return  RecipeMaterial.builder()
                .comment(recipeMaterialRequest.comment())
                .cost(recipeMaterialRequest.cost())
                .quantity(recipeMaterialRequest.quantity())
                .recipe(recipeMaterialRequest.recipe())
                .material(recipeMaterialRequest.material())
                .build();
    }
    public RecipeMaterialResponse toRecipeMaterialResponse(RecipeMaterial recipe) {
        return RecipeMaterialResponse.builder()
                .comment(recipe.getComment())
                .cost(recipe.getCost())
                .quantity(recipe.getQuantity())
                .recipe(recipe.getRecipe())
                .material(recipe.getMaterial())
                .build();
    }

    public RecipeMaterial backtoRecipeMaterial(RecipeMaterialUpdateRequest updateRequest){
        return RecipeMaterial.builder()
                .recipeMaterialId(updateRequest.id())
                .comment(updateRequest.comment())
                .cost(updateRequest.cost())
                .quantity(updateRequest.quantity())
                .build();
    }

}
