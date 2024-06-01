package com.breezevillepark_ms.bvp_ms.management;

import jakarta.validation.constraints.Email;

public record SettingUpdateRequest(
        Integer id,
        String companyAddress,

        @Email
        String companyEmail,
        String companyPhoneNumber,
        String companyName
) {
}
