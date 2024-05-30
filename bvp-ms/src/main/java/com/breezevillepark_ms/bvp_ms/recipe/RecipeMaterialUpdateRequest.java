package com.breezevillepark_ms.bvp_ms.recipe;

import java.math.BigDecimal;

public record RecipeMaterialUpdateRequest(
        Integer id,
        String comment,
        BigDecimal cost,
        Double quantity

) {
}
