package org.funding.domain.user.vo.enumType;

public enum Role {

    ROLE_NORMAL, ROLE_FINANCE, ROLE_ADMIN;

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equals(value)) {
                return role;
            }
        }

        throw new IllegalArgumentException("알수없는 권한: " + value);
    }
}
