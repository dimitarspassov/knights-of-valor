package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    Role findByRole(RoleName roleName);
}
