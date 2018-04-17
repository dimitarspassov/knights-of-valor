package com.dspassov.kovapi.areas.game.common;


import com.dspassov.kovapi.areas.game.entities.InventoryItem;
import com.dspassov.kovapi.areas.game.entities.Item;

public final class ItemFactory {

    public static InventoryItem createNewInventoryItem(Item item) {
        InventoryItem newItem = new InventoryItem();
        newItem.setItem(item);
        return newItem;
    }

}
