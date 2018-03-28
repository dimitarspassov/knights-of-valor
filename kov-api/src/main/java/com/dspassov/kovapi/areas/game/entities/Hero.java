package com.dspassov.kovapi.areas.game.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "heroes")
public class Hero {

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

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "gold")
    private Integer gold;

    @Column(name = "health")
    private Integer health;

    @Column(name = "strength")
    private Integer strength;

    @Column(name = "defense")
    private Integer defense;

    @Column(name = "stamina")
    private Integer stamina;

    @Column(name = "last_fight_date")
    private LocalDateTime lastFightDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "battle_set_id", referencedColumnName = "id")
    private BattleSet battleSet;

    @OneToOne(optional = false)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    public Hero() {
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
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

    public LocalDateTime getLastFightDate() {
        return lastFightDate;
    }

    public void setLastFightDate(LocalDateTime lastFightDate) {
        this.lastFightDate = lastFightDate;
    }

    public BattleSet getBattleSet() {
        return battleSet;
    }

    public void setBattleSet(BattleSet battleSet) {
        this.battleSet = battleSet;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
