package com.dspassov.kovapi.areas.users.common;

import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;

public final class RoleFactory {

    public static Role createNewRole(RoleName roleName) {
        Role role = new Role();
        role.setRole(roleName);
        return role;
    }

}

