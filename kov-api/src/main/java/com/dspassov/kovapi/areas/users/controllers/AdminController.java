package com.dspassov.kovapi.areas.users.controllers;


import com.dspassov.kovapi.areas.users.models.view.UserPageViewModel;
import com.dspassov.kovapi.areas.users.services.UserService;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/superadmin")
public class AdminController extends BaseController {

    private final UserService userService;


    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public String getAllAdministrators(@RequestParam("page") int page,
                                       @RequestParam("size") int size) {

        UserPageViewModel admins = this.userService.getAllAdminUsers(page, size);
        return this.objectToJson(admins);
    }

    @PostMapping("/new-admin/{username}")
    public String makeAdmin(@PathVariable(name = "username") String username) {

        return this.success(this.userService.makeAdmin(username));
    }


    @PostMapping("/remove-admin/{username}")
    public String removeAdmin(@PathVariable(name = "username") String username) {

        return this.success(this.userService.removeAdmin(username));
    }

}