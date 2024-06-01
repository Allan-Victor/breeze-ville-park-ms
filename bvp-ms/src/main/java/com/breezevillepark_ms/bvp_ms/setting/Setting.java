package com.breezevillepark_ms.bvp_ms.setting;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Setting {
    @Id
    @GeneratedValue
    private Integer settingsId;
    private String companyAddress;

    @Email
    private String companyEmail;
    private String companyPhoneNumber;
    private String companyName;

}
