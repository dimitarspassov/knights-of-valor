package com.dspassov.kovapi.areas.users.controllers;

import com.dspassov.kovapi.areas.users.models.RegisterUserBindingModel;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;


@RestController
public class AccountController extends BaseController {

    private final UserService userService;


    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
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

}