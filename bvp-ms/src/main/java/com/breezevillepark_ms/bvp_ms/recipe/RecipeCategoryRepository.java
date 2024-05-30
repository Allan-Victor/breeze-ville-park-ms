package com.breezevillepark_ms.bvp_ms.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Integer> {
    boolean existsByRecipeCategoryName(String categoryName);

}
