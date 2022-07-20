package lbd.fissst.securitylbd.security;

import lombok.Getter;

@Getter
public enum UserPermissions {

    USER_READ("user:read"),
    USER_EDIT("user:edit"),
    ADMIN("admin");

    private final String permission;

    UserPermissions(String permission) {
        this.permission = permission;
    }

}
