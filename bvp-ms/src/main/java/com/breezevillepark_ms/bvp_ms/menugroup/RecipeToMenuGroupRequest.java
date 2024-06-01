package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RecipeToMenuGroupRequest {
    private final Recipe recipe;

    public RecipeToMenuGroupRequest(@JsonProperty("recipe") Recipe recipe) {
        this.recipe = recipe;
    }
}
