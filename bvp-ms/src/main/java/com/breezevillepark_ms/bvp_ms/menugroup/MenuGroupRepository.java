package com.breezevillepark_ms.bvp_ms.menugroup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Integer> {
    boolean existsByGroupTitle(String groupTitle);

    Optional<MenuGroup> findByGroupTitle(String menuGroupTitle);

    @Query(value = """
            SELECT *
            FROM menu_group
            WHERE is_available_now = true
            """, nativeQuery = true
    )
    Page<MenuGroup> findAllAvailableMenuGroups(Pageable pageable);
}
