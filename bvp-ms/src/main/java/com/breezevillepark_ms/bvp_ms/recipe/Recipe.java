package com.breezevillepark_ms.bvp_ms.recipe;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.menugroup.MenuGroup;
import com.breezevillepark_ms.bvp_ms.order.OrderItem;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Entity
public class Recipe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "recipe_seq")
    @SequenceGenerator(name = "recipe_seq",
    sequenceName = "recipe_seq",
    allocationSize = 1)
    private Integer recipeId;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal profit;
    private BigDecimal totalCost;
    private int serving;
    private boolean canCook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuGroupId")
    private MenuGroup groups;

    @OneToMany(mappedBy = "recipe")
    private List<OrderItem> orderItems;


    @ManyToOne
    @JoinColumn(name = "recipe_category_id")
    private RecipeCategory recipeCategory;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeMaterial> recipeMaterials;
}
