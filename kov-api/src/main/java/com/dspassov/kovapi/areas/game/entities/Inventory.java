package com.dspassov.kovapi.areas.game.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "inventories")
public class Inventory {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "size")
    private Integer size;


    @ManyToMany
    @JoinTable(
            name = "inventories_items",
            joinColumns = {@JoinColumn(name = "inventory_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private Set<Item> items;

    public Inventory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
