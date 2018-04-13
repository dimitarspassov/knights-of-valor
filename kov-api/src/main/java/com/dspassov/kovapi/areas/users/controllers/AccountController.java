package com.dspassov.kovapi.areas.users.controllers;

import com.cloudinary.Cloudinary;
import com.dspassov.kovapi.areas.users.models.binding.RegisterUserBindingModel;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.areas.users.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;


@RestController
public class AccountController extends BaseController {

    private final UserService userService;
    private final Cloudinary cloudinary;


    @Autowired
    public AccountController(UserService userService, Cloudinary cloudinary) {
        this.userService = userService;
        this.cloudinary = cloudinary;
    }


    @PostMapping("/api/register")
    @ResponseBody
    public String register(@Valid @RequestBody RegisterUserBindingModel user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return this.error(bindingResult.getAllErrors()
                    .get(0).getDefaultMessage());
        }

        try {
            return this.success(this.userService.save(user));
        } catch (IllegalArgumentException exception) {
            return this.error(exception.getMessage());
        }

    }

    @GetMapping("/api/auth")
    public String validateCurrentToken() {
        return this.success("authenticated");
    }

    @GetMapping("/api/auth/admin")
    public String validateAdminToken() {
        return this.success("authenticated");
    }

    @GetMapping("/api/auth/superadmin")
    public String validateSuperAdminToken() {
        return this.success("authenticated");
    }

}