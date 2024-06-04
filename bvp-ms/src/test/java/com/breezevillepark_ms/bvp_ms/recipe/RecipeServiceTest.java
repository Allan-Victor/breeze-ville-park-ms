package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.exception.DuplicateResourceException;
import com.breezevillepark_ms.bvp_ms.menugroup.MenuGroup;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


class RecipeServiceTest {

    private RecipeService underTest;

    @Mock
    private RecipeRepository repository;

    @Mock
    private RecipeMapper recipeMapper;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new RecipeService(repository,recipeMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldAddRecipe() {
        // Given
        // ... given a recipe request
        String title = "Chicken Curry";
        RecipeRequest request = new RecipeRequest(
                title,
                "This creamy curry sauce is perfect for cooking chicken in until its fall-apart tender.",
                new BigDecimal("12345.6789"),
                new BigDecimal("12345.6789"),
                true
        );

        given(repository.existsByTitle(title)).willReturn(false);
        given(recipeMapper.toRecipe(request)).willReturn(new Recipe());
        given(repository.save(new Recipe())).willReturn(new Recipe());

        // When
        Integer recipeId = underTest.addRecipe(request);

        // Then
        then(repository).should().save(new Recipe());
        assertThat(recipeId).isEqualTo(new Recipe().getRecipeId());

        verify(repository, times(1)).existsByTitle(title);
        verify(repository, times(1)).save(new Recipe());
        verify(recipeMapper, times(1)).toRecipe(request);

    }

    @Test
    void itShouldThrowWhenExistsByTitleIsTrue() {
        // Given
        String title = "Chicken Curry";
        RecipeRequest request = new RecipeRequest(
                title,
                "This creamy curry sauce is perfect for cooking chicken in until its fall-apart tender.",
                new BigDecimal("12345.6789"),
                new BigDecimal("12345.6789"),
                true
        );
        given(repository.existsByTitle(title)).willReturn(true);

        // When
        // Then
        assertThatThrownBy(() -> underTest.addRecipe(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Recipe already exists");

        verify(repository, never()).save(any());

    }

    @Test
    void itShouldFindAllRecipes() {
        // Given
        Pageable pageable = PageRequest.of(0, 20, Sort.by("submittedAt").descending());
        Recipe one = new Recipe(
                1,
                "Curry",
                "tasty",
                new BigDecimal(1234.678),
                new BigDecimal(100),
                new BigDecimal(1234.678),
                20,
                true,
                new MenuGroup(),
                Collections.emptyList(),
                new RecipeCategory(),
                Collections.emptyList()
        );
        Recipe two = new Recipe(
                1,
                "biryani",
                "tasty",
                new BigDecimal(3234.678),
                new BigDecimal(400),
                new BigDecimal(1234.678),
                10,
                true,
                new MenuGroup(),
                Collections.emptyList(),
                new RecipeCategory(),
                Collections.emptyList()
        );
        List<Recipe> recipes = List.of(one,two);
        Page<Recipe> recipePage = new PageImpl<>(recipes,pageable, recipes.size());

        given(repository.findAll(pageable)).willReturn(recipePage);
        given(recipeMapper.toRecipeResponse(one)).willReturn(
                new RecipeResponse(
                        one.getRecipeId(),
                        one.getTitle(),
                        one.getDescription(),
                        one.getPrice(),
                        one.getProfit(),
                        one.getTotalCost(),
                        one.getServing(),
                        one.isCanCook(),
                        one.getGroups(),
                        one.getOrderItems(),
                        one.getRecipeCategory(),
                        one.getRecipeMaterials()
                )
        );
        given(recipeMapper.toRecipeResponse(two)).willReturn(
                new RecipeResponse(
                        two.getRecipeId(),
                        two.getTitle(),
                        two.getDescription(),
                        two.getPrice(),
                        two.getProfit(),
                        two.getTotalCost(),
                        two.getServing(),
                        two.isCanCook(),
                        two.getGroups(),
                        two.getOrderItems(),
                        two.getRecipeCategory(),
                        two.getRecipeMaterials()
                )
        );

        // When
        PageResponse<RecipeResponse> response = underTest.findAllRecipes(0, 20);

        // Then
        assertThat(response.getContent()).hasSize(2);
        assertThat(response.getPageNumber()).isEqualTo(recipePage.getNumber());
        assertThat(response.getPageSize()).isEqualTo(recipePage.getSize());
        assertThat(response.getTotalElements()).isEqualTo(recipePage.getTotalElements());
        assertThat(response.getTotalPages()).isEqualTo(recipePage.getTotalPages());
        assertThat(response.isFirst()).isEqualTo(recipePage.isFirst());
        assertThat(response.isLast()).isEqualTo(recipePage.isLast());

        verify(repository, times(1)).findAll(pageable);

    }

    @Test
    void itShouldFindByName() {
        // Given
        String title = "biryani";
        Recipe two = new Recipe(
                1,
                title,
                "tasty",
                new BigDecimal(3234.678),
                new BigDecimal(400),
                new BigDecimal(1234.678),
                10,
                true,
                new MenuGroup(),
                Collections.emptyList(),
                new RecipeCategory(),
                Collections.emptyList());

        given(repository.findByTitle(title)).willReturn(Optional.of(two));
        given(recipeMapper.toRecipeResponse(two)).willReturn(
                new RecipeResponse(
                        two.getRecipeId(),
                        two.getTitle(),
                        two.getDescription(),
                        two.getPrice(),
                        two.getProfit(),
                        two.getTotalCost(),
                        two.getServing(),
                        two.isCanCook(),
                        two.getGroups(),
                        two.getOrderItems(),
                        two.getRecipeCategory(),
                        two.getRecipeMaterials()

        ));

        // When

        RecipeResponse recipeResponse = underTest.findByName(title);

        // Then
        assertThat(recipeResponse.getTitle()).isEqualTo(title);

    }

    @Test
    void itShouldUpdateRecipe() {
        // Given
        RecipeUpdateRequest updateRequest = new RecipeUpdateRequest(
                1,
                "Chicken curry",
                "This creamy curry sauce is perfect for cooking chicken in until its fall-apart tender.",
                new BigDecimal("12345.6789"),
                new BigDecimal("12345.6789"),
                true
        );
        Recipe updatedRecipe = new Recipe(
                1,
                "Chicken curry",
                "This creamy curry sauce is perfect for cooking chicken in until its fall-apart tender.",
                new BigDecimal("12345.6789"),
                new BigDecimal("12345.6789"),
                new BigDecimal(1234.678),
                10,
                true,
                new MenuGroup(),
                Collections.emptyList(),
                new RecipeCategory(),
                Collections.emptyList()

        );

        given(recipeMapper.backtoRecipe(updateRequest)).willReturn(updatedRecipe);
        given(repository.save(updatedRecipe)).willReturn(updatedRecipe);

        // When
        Integer updated = underTest.updateRecipe(updateRequest);

        // Then
        then(repository).should().save(updatedRecipe);
        assertThat(updated).isEqualTo(updatedRecipe.getRecipeId());

    }

    @Test
    void itShouldRemoveRecipeById() {
        // Given
        int id = 1;

        // mock the services
        given(repository.existsById(id)).willReturn(true);

        // When
        underTest.removeRecipeById(id);

        // Then
        verify(repository).deleteById(id);

    }

    @Test
    void itShouldThrowWhenRemoveByIdDoesNotFindId() {
        // Given
        int id = -1;

        given(repository.existsById(id)).willReturn(false);

        // When
        assertThatThrownBy(()-> underTest.removeRecipeById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("The recipe does not exist");

        // Then
        verify(repository, never()).deleteById(id);

    }
}