package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
public class RecipeCategory extends BaseEntity {
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
