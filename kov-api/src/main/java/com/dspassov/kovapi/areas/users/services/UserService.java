package com.dspassov.kovapi.areas.users.services;


import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import com.dspassov.kovapi.areas.users.models.view.UserPageViewModel;
import com.dspassov.kovapi.areas.users.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    String save(RegisterUserBindingModel user);

    String makeAdmin(String username);

    UserPageViewModel getAllAdminUsers(int page, int size);

    String removeAdmin(String username);
}
