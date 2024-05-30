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
public class RecipeCategoryService {
    private final RecipeCategoryRepository categoryRepository;
    private final RecipeCategoryMapper recipeCategoryMapper;
    public Integer addRecipeCategory(RecipeCategoryRequest recipeCategoryRequest) {
        // check if recipe category exists in db
        if (categoryRepository.existsByRecipeCategoryName(recipeCategoryRequest.categoryName())) {
            throw new DuplicateResourceException("Recipe Category already exists");
        }
        // add Recipe to db
        RecipeCategory recipeCategory = recipeCategoryMapper.toRecipeCategory(recipeCategoryRequest);
        return categoryRepository.save(recipeCategory).getRecipeCategoryId();

    }

    public PageResponse<RecipeCategoryResponse> findAllRecipeCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("submittedAt").descending());
        Page<RecipeCategory> categories = categoryRepository.findAll(pageable);
        List<RecipeCategoryResponse> categoryResponse = categories.stream()
                .map(recipeCategoryMapper::toCategoryResponse)
                .toList();
        return new PageResponse<>(
                categoryResponse,
                categories.getNumber(),
                categories.getSize(),
                categories.getTotalElements(),
                categories.getTotalPages(),
                categories.isFirst(),
                categories.isLast()
        );
    }

    public Integer updateRecipeCategory(RecipeCategoryUpdateRequest recipeCategoryUpdateRequest) {
        RecipeCategory category = recipeCategoryMapper.backToRecipeCategory(recipeCategoryUpdateRequest);
        return categoryRepository.save(category).getRecipeCategoryId();
    }

    public void removeRecipeCategoryById(Integer recipeCategoryId) {
        if (!categoryRepository.existsById(recipeCategoryId)){
            throw new EntityNotFoundException("The recipe category does not exist");
        }
        categoryRepository.deleteById(recipeCategoryId);
    }
}
