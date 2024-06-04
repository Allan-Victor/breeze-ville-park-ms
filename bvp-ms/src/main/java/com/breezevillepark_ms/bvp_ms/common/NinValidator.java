package com.breezevillepark_ms.bvp_ms.common;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class NinValidator implements Predicate<String> {
    @Override
    public boolean test(String nin) {
        return nin.length() == 14;
    }
}
