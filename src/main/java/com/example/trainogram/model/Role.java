package com.example.trainogram.model;


import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER(0, "USER"),
    ADMIN(1, "ADMIN");
    private String authority;
    private Integer id;

    Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public static Role roleFromId(Integer id) {
        for (Role r : Role.values()) {
            if (r.getId().compareTo(id) == 0) {
                return r;
            }
        }
        return null;
    }

    public String getAuthority() {
        return this.authority;
    }

    public Integer getId() {
        return this.id;
    }
}
