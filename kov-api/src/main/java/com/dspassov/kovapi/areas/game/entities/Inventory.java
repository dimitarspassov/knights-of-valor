package com.dspassov.kovapi.areas.game.entities;

import com.dspassov.kovapi.exceptions.InventoryException;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "inventories")
public class Inventory {

    //todo: extract
    private static final int SIZE_MIN = 5;
    private static final int SIZE_MAX = 20;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Min(SIZE_MIN)
    @Max(SIZE_MAX)
    @Column(name = "size")
    private Integer size = SIZE_MIN;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "inventories_inventory_items",
            joinColumns = {@JoinColumn(name = "inventory_id")},
            inverseJoinColumns = {@JoinColumn(name = "inventory_item_id")})
    private List<InventoryItem> items;

    public Inventory() {
        this.setItems(new ArrayList<>());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    public void setItems(List<InventoryItem> items) {
        this.items = items;
    }

    public Integer getItemsCount() {
        int totalItems = 0;

        for (InventoryItem item : items) {
            totalItems += item.getCount();
        }
        return totalItems;
    }

    public void addItem(InventoryItem item) {

        if (this.getItemsCount() + item.getCount() > this.size) {
            throw new InventoryException();
        }

        List<InventoryItem> itemList = this.items.stream()
                .filter(i -> i.getItem().getId().equals(item.getItem().getId()))
                .limit(1)
                .collect(Collectors.toList());

        if (itemList.size() != 0) {
            InventoryItem current = itemList.get(0);
            current.setCount(current.getCount() + item.getCount());
            return;
        }

        this.getItems().add(item);
    }

    public void incrementSize() {
        if (this.size >= SIZE_MAX) {
            throw new InventoryException(ResponseMessageConstants.INVENTORY_MAX_SIZE_REACHED);
        }
        this.size++;
    }

}
