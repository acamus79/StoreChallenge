package com.aec.store.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of permissions for administrative actions.
 */
@RequiredArgsConstructor
public enum Permission {

    /**
     * Permission to read administrative data.
     */
    ADMIN_READ("admin:read"),

    /**
     * Permission to update administrative data.
     */
    ADMIN_UPDATE("admin:update"),

    /**
     * Permission to create administrative data.
     */
    ADMIN_CREATE("admin:create"),

    /**
     * Permission to delete administrative data.
     */
    ADMIN_DELETE("admin:delete");

    /**
     * The actual permission string.
     */
    @Getter
    private final String permission;
}
