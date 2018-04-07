package com.dspassov.kovapi.repositories;

import com.dspassov.kovapi.areas.users.entities.User;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    User findByUsername(String username);

}
