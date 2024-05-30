package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMaterialService recipeMaterialService;
    private final RecipeCategoryService recipeCategoryService;

    @PostMapping("/add/recipe")
    public ResponseEntity<Integer> createRecipe(@Valid @RequestBody RecipeRequest recipeRequest){
        return ok(recipeService.addRecipe(recipeRequest));

    }


    @GetMapping
    public ResponseEntity<PageResponse<RecipeResponse>> getAllRecipes(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size){
        return ok(recipeService.findAllRecipes(page, size));
    }

    @GetMapping("/{recipe-title}")
    public ResponseEntity<RecipeResponse> getRecipeByName(@PathVariable("recipe-title") String recipeTitle){
        return ok(recipeService.findByName(recipeTitle));
    }

    @PatchMapping("/update/recipe")
    public ResponseEntity<Integer> updateRecipe(@Valid @RequestBody RecipeUpdateRequest recipeUpdateRequest){
        return  ok(recipeService.updateRecipe(recipeUpdateRequest));
    }

    @DeleteMapping("/delete/{recipeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteRecipe(@PathVariable("customerId") Integer recipeId){
        recipeService.removeRecipeById(recipeId);
        return ResponseEntity.accepted().build();
    }

    /*
    * For Recipe Materials
    * */

    @PostMapping("/add_recipe_material")
    public ResponseEntity<Integer> createRecipeMaterial(@Valid @RequestBody RecipeMaterialRequest recipeMaterialRequest){
        return ok(recipeMaterialService.addRecipeMaterial(recipeMaterialRequest));
    }

    @GetMapping("/recipe_materials")
    public ResponseEntity<PageResponse<RecipeMaterialResponse>> getAllRecipeMaterials(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                      @RequestParam(name = "size", defaultValue = "10") int size){
        return ok(recipeMaterialService.findAllRecipeMaterials(page, size));
    }

    @PatchMapping("/update/recipe_material")
    public ResponseEntity<Integer> updateRecipeMaterial(@Valid @RequestBody RecipeMaterialUpdateRequest recipeMaterialUpdateRequest){
        return  ok(recipeMaterialService.updateRecipeMaterial(recipeMaterialUpdateRequest));
    }

    @DeleteMapping("/delete/recipe_material/{recipe_materialId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteRecipeMaterial(@PathVariable("recipe_materialId") Integer recipeMaterialId){
        recipeMaterialService.removeRecipeMaterialById(recipeMaterialId);
        return ResponseEntity.accepted().build();
    }

    /*
     * For Recipe Category
     * */

    @PostMapping("/add_recipe_category")
    public ResponseEntity<Integer> createRecipeCategory(@Valid @RequestBody RecipeCategoryRequest recipeCategoryRequest){
        return ok(recipeCategoryService.addRecipeCategory(recipeCategoryRequest));
    }

    @GetMapping("/recipe_categories")
    public ResponseEntity<PageResponse<RecipeCategoryResponse>> getAllRecipeCategories(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                                      @RequestParam(name = "size", defaultValue = "10") int size){
        return ok(recipeCategoryService.findAllRecipeCategories(page, size));
    }

    @PatchMapping("/update/recipe_category")
    public ResponseEntity<Integer> updateRecipeCategory(@Valid @RequestBody RecipeCategoryUpdateRequest recipeCategoryUpdateRequest){
        return  ok(recipeCategoryService.updateRecipeCategory(recipeCategoryUpdateRequest));
    }

    @DeleteMapping("/delete/recipe_category/{recipe_categoryId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteRecipeCategory(@PathVariable("recipe_categoryId") Integer recipeCategoryId){
        recipeCategoryService.removeRecipeCategoryById(recipeCategoryId);
        return ResponseEntity.accepted().build();
    }
}
