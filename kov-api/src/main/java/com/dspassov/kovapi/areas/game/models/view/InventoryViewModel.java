package com.dspassov.kovapi.areas.game.models.view;

import java.util.List;

public class InventoryViewModel {

    private Integer size;
    private List<ItemViewModel> items;

    public InventoryViewModel() {
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
}
