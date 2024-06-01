package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuGroupResponse {
    private Integer menuGroupId;
    private String description;
    private String groupTitle;
    private List<Recipe> recipes;
    private boolean isActive;
    private boolean isAvailableNow;
    private LocalDate availableDay;
}
