package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
import com.breezevillepark_ms.bvp_ms.recipe.RecipeUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class MenuGroupMapper {
    public MenuGroup toMenuGroup(MenuGroupRequest menuGroupRequest){
        return MenuGroup.builder()
                .groupTitle(menuGroupRequest.groupTitle())
                .description(menuGroupRequest.description())
                .isActive(menuGroupRequest.isActive())
                .isAvailableNow(menuGroupRequest.isAvailableNow())
                .availableDay(menuGroupRequest.availableDay())
                .build();
    }
    public MenuGroupResponse toMenuGroupResponse(MenuGroup group){
        return MenuGroupResponse.builder()
                .menuGroupId(group.getMenuGroupId())
                .groupTitle(group.getGroupTitle())
                .description(group.getDescription())
                .recipes(group.getRecipes())
                .isActive(group.isActive())
                .isAvailableNow(group.isAvailableNow())
                .availableDay(group.getAvailableDay())
                .build();
    }
    public MenuGroup backToMenuGroup(MenuGroupUpdateRequest updateRequest){
        return MenuGroup.builder()
                .menuGroupId(updateRequest.id())
                .groupTitle(updateRequest.groupTitle())
                .description(updateRequest.description())
                .isActive(updateRequest.isActive())
                .isAvailableNow(updateRequest.isAvailableNow())
                .availableDay(updateRequest.availableDay())
                .build();
    }
}
