package com.dspassov.kovapi.areas.game.models.service;

import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;

public class HeroCombatServiceModel {

    private String id;
    private String name;
    private Integer level;
    private Integer health;
    private Integer strength;
    private Integer stamina;
    private Integer defense;
    private BattleSetViewModel battleSet;

    public HeroCombatServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public BattleSetViewModel getBattleSet() {
        return battleSet;
    }

    public void setBattleSet(BattleSetViewModel battleSet) {
        this.battleSet = battleSet;
    }
}
