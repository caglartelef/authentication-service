package com.caglartelef.authenticationservice.enums;

import java.io.Serializable;
import java.util.Set;

public enum UserRoles implements Serializable {
    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    UserRoles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRoles getEnumFromValue(String value) {
        for (UserRoles v : values())
            if (v.name().equalsIgnoreCase(value))
                return v;
        return null;
    }

    public static UserRoles getEnumFromValue(Set<String> values) {
        for (UserRoles arg : values()) {
            for (String value : values) {
                if (arg.name().equalsIgnoreCase(value)) {
                    return arg;
                }
            }
        }
        return null;
    }
}