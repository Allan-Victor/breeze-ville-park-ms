package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import com.breezevillepark_ms.bvp_ms.exception.DuplicateResourceException;
import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
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
public class MenuGroupService {
    private final MenuGroupRepository groupRepository;
    private final MenuGroupMapper groupMapper;

    public Integer createMenuGroup(MenuGroupRequest menuGroupRequest) {
        // check if it exists in db
        if (groupRepository.existsByGroupTitle(menuGroupRequest.groupTitle())){
            throw new DuplicateResourceException("The menu group already exists");
        }
        MenuGroup menuGroup = groupMapper.toMenuGroup(menuGroupRequest);
        return groupRepository.save(menuGroup).getMenuGroupId();
    }

    public PageResponse<MenuGroupResponse> findAllMenuGroups(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
        Page<MenuGroup> menuGroups = groupRepository.findAll(pageable);
        List<MenuGroupResponse> responseList = menuGroups.stream()
                .map(groupMapper::toMenuGroupResponse)
                .toList();
        return new PageResponse<>(
                responseList,
                menuGroups.getNumber(),
                menuGroups.getSize(),
                menuGroups.getTotalElements(),
                menuGroups.getTotalPages(),
                menuGroups.isFirst(),
                menuGroups.isLast()
        );
    }

    public MenuGroupResponse findByName(String menuGroupTitle) {
        return groupRepository.findByGroupTitle(menuGroupTitle)
                .map(groupMapper::toMenuGroupResponse)
                .orElseThrow(() -> new EntityNotFoundException("No MenuGroup found with that name"));
    }

    public Integer updateMenuGroup(MenuGroupUpdateRequest menuGroupUpdateRequest) {
        MenuGroup menuGroup = groupMapper.backToMenuGroup(menuGroupUpdateRequest);
        return groupRepository.save(menuGroup).getMenuGroupId();
    }

    public void removeRecipeById(Integer menuGroupId) {
        if (!groupRepository.existsById(menuGroupId)){
            throw new EntityNotFoundException("The menu group does not exist");
        }
        groupRepository.deleteById(menuGroupId);
    }

    public void addRecipeToMenu(RecipeToMenuGroupRequest request, String menuGroupName) {
        // check availability of menu group
        MenuGroup menuGroup = groupRepository.findByGroupTitle(menuGroupName)
                .orElseThrow(() -> new EntityNotFoundException("The Menu group you want to update does not exist"));

        // get recipe from request
        Recipe recipe = request.getRecipe();

        recipe.setGroups(menuGroup);
        menuGroup.getRecipes().add(recipe);

        // update menu group
        groupRepository.save(menuGroup);
    }

    public void removeRecipeFromMenu(Integer id, RecipeToMenuGroupRequest request) {
        // check if the menu group exists
        MenuGroup menu = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The Menu group does not exist"));

           Recipe recipe = request.getRecipe();
           menu.getRecipes().remove(recipe);

           // update menu group
            groupRepository.save(menu);
    }

    public PageResponse<MenuGroupResponse> findAvailableMenuGroups(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
        Page<MenuGroup> menuGroups = groupRepository.findAllAvailableMenuGroups(pageable);
        List<MenuGroupResponse> responseList = menuGroups.stream()
                .map(groupMapper::toMenuGroupResponse)
                .toList();
        return new PageResponse<>(
                responseList,
                menuGroups.getNumber(),
                menuGroups.getSize(),
                menuGroups.getTotalElements(),
                menuGroups.getTotalPages(),
                menuGroups.isFirst(),
                menuGroups.isLast()
        );
    }
}
