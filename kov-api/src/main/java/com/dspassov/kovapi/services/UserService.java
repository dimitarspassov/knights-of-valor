package com.dspassov.kovapi.services;


import com.dspassov.kovapi.areas.users.models.RegisterUserBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String save(RegisterUserBindingModel user);
}
