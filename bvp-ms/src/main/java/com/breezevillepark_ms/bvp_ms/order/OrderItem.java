package com.breezevillepark_ms.bvp_ms.order;

import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq",
            sequenceName = "order_item_seq",
            allocationSize = 1)
    private Integer orderItemId;
    private String comment;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    private BigDecimal subTotal;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
