package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.exception.DuplicateResourceException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    public Integer addRecipe(RecipeRequest recipeRequest) {
        // check if recipe exists in db
        if (recipeRepository.existsByTitle(recipeRequest.title())) {
            throw new DuplicateResourceException("Recipe already exists");
        }
        // add Recipe to db
        Recipe recipe = recipeMapper.toRecipe(recipeRequest);
        return recipeRepository.save(recipe).getRecipeId();
    }

    public PageResponse<RecipeResponse> findAllRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("submittedAt").descending());
        Page<Recipe> recipes = recipeRepository.findAll(pageable);
        List<RecipeResponse> recipeResponses = recipes.stream()
                .map(recipeMapper::toRecipeResponse)
                .toList();
        return new PageResponse<>(
                recipeResponses,
                recipes.getNumber(),
                recipes.getSize(),
                recipes.getTotalElements(),
                recipes.getTotalPages(),
                recipes.isFirst(),
                recipes.isLast()
        );
    }

    public RecipeResponse findByName(String recipeTitle) {
        return recipeRepository.findByTitle(recipeTitle)
                .map(recipeMapper::toRecipeResponse)
                .orElseThrow(() -> new EntityNotFoundException("No recipe found with that name"));

    }

    public Integer updateRecipe(RecipeUpdateRequest recipeUpdateRequest) {
        Recipe recipe = recipeMapper.backtoRecipe(recipeUpdateRequest);
        return recipeRepository.save(recipe).getRecipeId();

}


    public void removeRecipeById(Integer recipeId) {
        if (!recipeRepository.existsById(recipeId)){
           throw new EntityNotFoundException("The recipe does not exist");
        }
        recipeRepository.deleteById(recipeId);
    }
}
