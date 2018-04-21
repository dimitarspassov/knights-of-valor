package com.dspassov.kovapi.entity;


import com.dspassov.kovapi.areas.game.entities.*;
import com.dspassov.kovapi.exceptions.InventoryException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;


public class InventoryTests {

    private Inventory inventory;

    @Before
    public void init() {
        this.inventory = new Inventory();
    }


    @Test
    public void testGetItemsCount_onEmptyInventory_expectZero() {

        //act
        int actual = this.inventory.getItemsCount();

        //assert
        assertEquals(
                "Items on empty inventory is not 0",
                0,
                actual);
    }

    @Test
    public void testGetItemsCount_onInventoryWithItems_expectTheSumOfInventoryItemsCount() {

        //arrange
        final int ITEM_COUNT = 3;
        InventoryItem a = new InventoryItem();
        a.setCount(ITEM_COUNT);
        InventoryItem b = new InventoryItem();

        List<InventoryItem> itemList = new ArrayList<>();
        itemList.add(a);
        itemList.add(b);

        this.inventory.setItems(itemList);

        //act
        int actual = a.getCount() + b.getCount();
        int expected = this.inventory.getItemsCount();

        //assert
        assertEquals(
                "Inventory does not calculate its count correctly",
                expected,
                actual);
    }

    @Test
    public void testAddItem_addNewInventoryItemOnEmptyInventory_expectCountOfItemsToBeOne() {

        //act
        this.inventory.addItem(new InventoryItem());

        int expected = 1;
        int actual = this.inventory.getItemsCount();

        //assert
        assertEquals(
                "Item not added successfully",
                expected,
                actual);
    }

    @Test
    public void testAddItem_multipleInventoryItemsSomeOfWhichPointToTheSameItem_expectCountOfItemsToBeCorrect() {

        //arrange
        Item weapon = new Weapon();
        weapon.setId("testWeapon");
        Item shield = new Shield();
        shield.setId("testShield");

        InventoryItem a = new InventoryItem();
        a.setItem(weapon);
        InventoryItem b = new InventoryItem();
        b.setItem(weapon);
        b.setCount(3);
        InventoryItem c = new InventoryItem();
        c.setItem(shield);

        //act
        int expected = a.getCount() + b.getCount() + c.getCount();

        this.inventory.addItem(a);
        this.inventory.addItem(b);
        this.inventory.addItem(c);

        int actual = this.inventory.getItemsCount();

        //assert
        assertEquals(
                "Items not added successfully",
                expected,
                actual);
    }

    @Test
    public void testAddItem_addItemsWithCountBiggerThanTheSizeOfTheInventory_shouldThrowException() {

        //arrange
        InventoryItem item = new InventoryItem();
        item.setCount(50);

        //act

        try {
            this.inventory.addItem(item);
            fail("Expected InventoryException.");
        } catch (InventoryException ie) {
            //assert
            assertEquals(ie.getMessage(), ResponseMessageConstants.INVENTORY_FULL);
        }

    }

    @Test
    public void testIncrementSize_incrementTheSizeOfTheInventory_expectSizeToIncreaseAccordingly() {

        //act
        int initialSize = this.inventory.getSize();
        this.inventory.incrementSize();
        int expected = initialSize + 1;
        int actual = this.inventory.getSize();

        //assert
        assertEquals(
                "Size not incremented successfully",
                expected,
                actual);
    }

    @Test
    public void testIncrementSize_tryToIncrementBeyondTheMaxSizeConstraint_shouldThrowException() {

        try {
            this.inventory.setSize(20);
            this.inventory.incrementSize();
            fail("Expected InventoryException");
        } catch (InventoryException ie) {
            //assert
            assertEquals(ie.getMessage(), ResponseMessageConstants.INVENTORY_MAX_SIZE_REACHED);
        }
    }
}
