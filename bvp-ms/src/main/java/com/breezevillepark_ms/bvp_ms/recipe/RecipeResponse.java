package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.menugroup.MenuGroup;
import com.breezevillepark_ms.bvp_ms.order.OrderItem;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeResponse {
    private Integer recipeId;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal profit;
    private BigDecimal totalCost;
    private int serving;
    private boolean canCook;
    private MenuGroup groups;
    private List<OrderItem> orderItems;
    private RecipeCategory recipeCategory;
    private List<RecipeMaterial> recipeMaterials;
}
