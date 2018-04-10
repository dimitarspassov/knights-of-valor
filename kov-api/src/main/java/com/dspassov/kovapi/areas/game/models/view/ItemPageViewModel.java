package com.dspassov.kovapi.areas.game.models.view;

import java.util.ArrayList;
import java.util.List;

public class ItemPageViewModel {

    private Integer size;
    private List<ItemViewModel> items;
    private Integer allPages;

    public ItemPageViewModel() {
        this.setItems(new ArrayList<>());
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<ItemViewModel> getItems() {
        return items;
    }

    public void setItems(List<ItemViewModel> items) {
        this.items = items;
    }

    public Integer getAllPages() {
        return allPages;
    }

    public void setAllPages(Integer allPages) {
        this.allPages = allPages;
    }
}
