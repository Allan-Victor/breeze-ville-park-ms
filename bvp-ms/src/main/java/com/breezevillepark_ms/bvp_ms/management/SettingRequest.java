package com.breezevillepark_ms.bvp_ms.management;

import jakarta.validation.constraints.Email;

public record SettingRequest(
        String companyAddress,

        @Email
        String companyEmail,
        String companyPhoneNumber,
        String companyName
) {
}
