package com.dspassov.kovapi.areas.game.models.view;

import java.time.LocalDateTime;

public class HeroViewModel {

    private String name;
    private Integer level;
    private Integer experienceTillNextLevel;
    private Long gold;
    private Integer health;
    private Integer strength;
    private Integer defense;
    private Integer stamina;
    private LocalDateTime lastFightDate;

    public HeroViewModel() {
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

    public Integer getExperienceTillNextLevel() {
        return experienceTillNextLevel;
    }

    public void setExperienceTillNextLevel(Integer experienceTillNextLevel) {
        this.experienceTillNextLevel = experienceTillNextLevel;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
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

}
