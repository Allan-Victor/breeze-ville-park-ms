package com.breezevillepark_ms.bvp_ms.menugroup;

import java.time.LocalDate;

public record MenuGroupUpdateRequest(
        Integer id,
         String description,
         String groupTitle,
         boolean isActive,
         boolean isAvailableNow,
         LocalDate availableDay
) {
}
