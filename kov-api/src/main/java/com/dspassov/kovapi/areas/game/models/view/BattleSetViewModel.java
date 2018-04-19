package com.dspassov.kovapi.areas.game.models.view;

public class BattleSetViewModel {

    private ItemViewModel weapon;
    private ItemViewModel shield;
    private ItemViewModel armor;

    public BattleSetViewModel() {
    }

    public ItemViewModel getWeapon() {
        return weapon;
    }

    public void setWeapon(ItemViewModel weapon) {
        this.weapon = weapon;
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
