package com.breezevillepark_ms.bvp_ms.recipe;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class RecipeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "recipe_category_seq")
    @SequenceGenerator(name = "recipe_category_seq",
            sequenceName = "recipe_category_seq",
            allocationSize = 1)
    private Integer recipeCategoryId;
    private String recipeCategoryName;

    @OneToMany(mappedBy = "recipeCategory" )
    private List<Recipe> recipes;
}
