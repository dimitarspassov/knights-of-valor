package com.dspassov.kovapi.areas.users.models.view;

import java.util.List;

public class UserPageViewModel {

    private Integer size;
    private List<UserViewModel> users;
    private Integer allPages;

    public UserPageViewModel() {
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<UserViewModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserViewModel> users) {
        this.users = users;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }
}
