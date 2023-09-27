package com.aec.store.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aec.store.enums.Permission.*;

/**
 * Enumeration of roles with associated permissions.
 */
@RequiredArgsConstructor
public enum Role {

    /**
     * User role with no specific permissions.
     */
    USER(Collections.emptySet()),

    /**
     * Admin role with a set of administrative permissions.
     */
    ADMIN(Set.of(
            ADMIN_READ,
            ADMIN_UPDATE,
            ADMIN_DELETE,
            ADMIN_CREATE
    ));

    /**
     * The set of permissions associated with the role.
     */
    @Getter
    private final Set<Permission> permissions;

    /**
     * Get the authorities associated with the role and its permissions.
     *
     * @return A list of authorities for the role.
     */
    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
