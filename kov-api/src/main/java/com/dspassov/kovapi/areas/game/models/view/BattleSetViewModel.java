package com.dspassov.kovapi.areas.game.models.view;

public class BattleSetViewModel {

    private ItemViewModel sword;
    private ItemViewModel shield;
    private ItemViewModel armor;

    public BattleSetViewModel() {
    }

    public ItemViewModel getSword() {
        return sword;
    }

    public void setSword(ItemViewModel sword) {
        this.sword = sword;
    }

    public ItemViewModel getShield() {
        return shield;
    }

    public void setShield(ItemViewModel shield) {
        this.shield = shield;
    }

    public ItemViewModel getArmor() {
        return armor;
    }

    public void setArmor(ItemViewModel armor) {
        this.armor = armor;
    }
}
