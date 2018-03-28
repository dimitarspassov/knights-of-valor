package com.dspassov.kovapi.areas.game.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "neutral_units")
public class NeutralUnit {


    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Column(name = "health")
    private Integer health;

    @Column(name = "strength")
    private Integer stength;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "stamina")
    private Integer stamina;

    @Column(name = "loot_gold")
    private Integer lootGold;

    @Column(name = "image")
    private String image;

    public NeutralUnit() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getStength() {
        return stength;
    }

    public void setStength(Integer stength) {
        this.stength = stength;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getLootGold() {
        return lootGold;
    }

    public void setLootGold(Integer lootGold) {
        this.lootGold = lootGold;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
