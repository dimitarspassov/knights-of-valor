package com.dspassov.kovapi.areas.users.services;


import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String save(RegisterUserBindingModel user);
}
