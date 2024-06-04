package com.breezevillepark_ms.bvp_ms.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),

    CHEF_READ("chef:read"),
    CHEF_UPDATE("chef:update"),
    CHEF_CREATE("chef:create"),
    CHEF_DELETE("chef:delete");

    @Getter
    private final String permission;
}
