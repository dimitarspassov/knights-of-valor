package com.dspassov.kovapi.areas.users.models.service;

import com.dspassov.kovapi.areas.users.enumerations.RoleName;

public class RoleServiceModel {

    private String id;
    private RoleName role;

    public RoleServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }
}
