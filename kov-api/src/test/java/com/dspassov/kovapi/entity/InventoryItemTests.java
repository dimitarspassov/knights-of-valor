package com.dspassov.kovapi.entity;


import com.dspassov.kovapi.areas.game.entities.InventoryItem;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class InventoryItemTests {


    @Test
    public void testDecrementCount_onInitialItemState_expectCountToBecomeZero() {

        //arrange
        InventoryItem item = new InventoryItem();

        //act
        item.decrementCount();
        int actual = item.getCount();

        //assert
        assertEquals(
                "Count not decremented correctly",
                0,
                actual
        );
    }


}
