package com.breezevillepark_ms.bvp_ms.recipe;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeCategoryResponse {
    private Integer recipeCategoryId;
    private String recipeCategoryName;
    private List<Recipe> recipes;
}
