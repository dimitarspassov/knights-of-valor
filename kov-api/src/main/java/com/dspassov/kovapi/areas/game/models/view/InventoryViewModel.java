package com.dspassov.kovapi.areas.game.models.view;

import java.util.Set;

public class InventoryViewModel {

    private Integer size;
    private Set<ItemViewModel> items;

    public InventoryViewModel() {
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<ItemViewModel> getItems() {
        return items;
    }

    public void setItems(Set<ItemViewModel> items) {
        this.items = items;
    }
}
