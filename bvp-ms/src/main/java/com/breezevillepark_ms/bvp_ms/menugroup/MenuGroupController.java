package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("menuGroup")
@RequiredArgsConstructor
public class MenuGroupController {
    private final MenuGroupService service;

    @PostMapping("/add")
    public ResponseEntity<Integer> createMenuGroup(@Valid @RequestBody MenuGroupRequest menuGroupRequest){
        return ResponseEntity.ok(service.createMenuGroup(menuGroupRequest));
    }

    @PostMapping("/add_recipe")
    @ResponseStatus(value = HttpStatus.OK)
    public void addRecipeToMenuGroup(@RequestBody RecipeToMenuGroupRequest request, @RequestParam String menuGroupName){
        service.addRecipeToMenu(request,menuGroupName);

    }


    @GetMapping("/all")
    public ResponseEntity<PageResponse<MenuGroupResponse>> getAllMenuGroups(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.findAllMenuGroups(page, size));
    }

    @GetMapping("/available")
    public ResponseEntity<PageResponse<MenuGroupResponse>> getAvailableMenuGroups(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                            @RequestParam(name = "size", defaultValue = "10") int size){
        return ResponseEntity.ok(service.findAvailableMenuGroups(page, size));
    }


    @GetMapping("/{menuGroup_title}")
    public ResponseEntity<MenuGroupResponse> getMenuGroupByName(@RequestParam(name = "menuGroup_title") String menuGroupTitle){
        return ok(service.findByName(menuGroupTitle));
    }

    @PatchMapping("/update/menuGroup")
    public ResponseEntity<Integer> updateMenuGroup(@Valid @RequestBody MenuGroupUpdateRequest menuGroupUpdateRequest){
        return  ok(service.updateMenuGroup(menuGroupUpdateRequest));
    }

    @DeleteMapping("/delete/{menuGroupId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deleteMenuGroup(@PathVariable("menuGroupId") Integer menuGroupId){
        service.removeRecipeById(menuGroupId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{menuGroupId}/recipe")
    public ResponseEntity<?> deleteRecipeFromGroup(@PathVariable("menuGroupId") Integer menuGroupId,
                                                   RecipeToMenuGroupRequest request ){
        service.removeRecipeFromMenu(menuGroupId, request);
        return ResponseEntity.accepted().build();
    }
}
