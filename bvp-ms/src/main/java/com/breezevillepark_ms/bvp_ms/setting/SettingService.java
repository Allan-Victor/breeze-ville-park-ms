package com.breezevillepark_ms.bvp_ms.setting;

import com.breezevillepark_ms.bvp_ms.common.PhoneNumberValidator;
import com.breezevillepark_ms.bvp_ms.management.SettingRequest;
import com.breezevillepark_ms.bvp_ms.management.SettingUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository repository;
    private final PhoneNumberValidator validator;

    public Integer saveSetting(SettingRequest settingRequest) {
        String phoneNumber = settingRequest.companyPhoneNumber();
        if (!validator.test(settingRequest.companyPhoneNumber())) {
            throw new IllegalStateException("Phone Number " + phoneNumber + " is not valid");
        }

        Setting setting = Setting.builder()
                .companyAddress(settingRequest.companyAddress())
                .companyEmail(settingRequest.companyEmail())
                .companyPhoneNumber(phoneNumber)
                .companyName(settingRequest.companyName())
                .build();
        return repository.save(setting).getSettingsId();
    }

    public List<Setting> loadSetting() {
        return repository.findAll();


    }

    public Integer updateSetting(SettingUpdateRequest settingUpdateRequest) {
        Setting update = Setting.builder()
                .settingsId(settingUpdateRequest.id())
                .companyAddress(settingUpdateRequest.companyAddress())
                .companyEmail(settingUpdateRequest.companyEmail())
                .companyPhoneNumber(settingUpdateRequest.companyPhoneNumber())
                .companyName(settingUpdateRequest.companyName())
                .build();
        return repository.save(update).getSettingsId();
    }
}
