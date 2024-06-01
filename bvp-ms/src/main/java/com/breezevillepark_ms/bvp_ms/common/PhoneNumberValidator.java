package com.breezevillepark_ms.bvp_ms.common;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class PhoneNumberValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s.length() == 10 && s.startsWith("07");
    }
}
