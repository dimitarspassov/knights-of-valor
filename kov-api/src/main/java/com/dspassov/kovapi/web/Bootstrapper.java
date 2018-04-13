package com.dspassov.kovapi.web;


import com.dspassov.kovapi.areas.users.services.RoleService;
import com.dspassov.kovapi.areas.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrapper implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public Bootstrapper(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @Override
    public void run(String... args) throws Exception {

        this.roleService.initiateRoles();

    }
}
