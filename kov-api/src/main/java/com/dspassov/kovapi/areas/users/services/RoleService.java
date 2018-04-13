package com.dspassov.kovapi.areas.users.services;


import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import com.dspassov.kovapi.areas.users.models.service.RoleServiceModel;

public interface RoleService {

    void initiateRoles();

    RoleServiceModel getRole(RoleName roleName);
}
