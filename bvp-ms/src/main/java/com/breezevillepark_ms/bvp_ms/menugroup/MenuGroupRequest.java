package com.breezevillepark_ms.bvp_ms.menugroup;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public record MenuGroupRequest(
        @NotNull(message = "Description should not be null")
        @NotEmpty(message = "Description should not be empty")
        String description,

        @NotNull(message = "GroupTitle should not be null")
        @NotEmpty(message = "GroupTitle should not be empty")
        String groupTitle,

        @NotNull(message = "101")
        @NotEmpty(message = "101")
        boolean isActive,

        @NotNull(message = "102")
        @NotEmpty(message = "102")
        boolean isAvailableNow,

        @NotNull(message = "Available Day should not be null")
        @NotEmpty(message = "Available Day should not be empty")
        LocalDate availableDay
) {
}
