package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.users.entities.Role;
import com.dspassov.kovapi.areas.users.entities.User;

import com.dspassov.kovapi.areas.users.enumerations.RoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByUsername(String username);

    List<User> findByRolesContaining(Role superadminRole);

    Page<User> findAllByRolesContaining(Pageable pageable, Role role);
}
