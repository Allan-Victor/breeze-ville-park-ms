package com.breezevillepark_ms.bvp_ms.menugroup;

import com.breezevillepark_ms.bvp_ms.common.BaseEntity;
import com.breezevillepark_ms.bvp_ms.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "menu_group")
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "menu_group_seq")
    @SequenceGenerator(name = "menu_group_seq",
    sequenceName = "menu_group_seq", allocationSize = 1)
    private Integer menuGroupId;

    private String description;

    @Column(unique = true)
    private String groupTitle;

    @OneToMany(mappedBy = "groups")
    private List<Recipe> recipes;

    private boolean isActive;
    private boolean isAvailableNow;
    private LocalDate availableDay;

}
