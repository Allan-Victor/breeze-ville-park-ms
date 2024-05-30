package com.breezevillepark_ms.bvp_ms.recipe;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    boolean existsByTitle(String title);

    Optional<Recipe> findByTitle(String recipeTitle);
}
