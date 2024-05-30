package com.breezevillepark_ms.bvp_ms.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.breezevillepark_ms.bvp_ms.employee.Permission.*;

@RequiredArgsConstructor
public enum Role {
    MANAGER(Collections.emptySet()),
    RECEPTIONIST(Collections.emptySet()),
    CASHIER(Collections.emptySet()),
    CHEF(Collections.emptySet()),
    USER(Collections.emptySet()),
    ADMIN(Set.of(
            ADMIN_CREATE,
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE
    ));

    @Getter
    private final Set<Permission> permissions;
    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;


    }
}
