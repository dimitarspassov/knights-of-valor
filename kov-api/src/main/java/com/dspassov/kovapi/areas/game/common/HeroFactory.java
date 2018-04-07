package com.dspassov.kovapi.areas.game.common;

import com.dspassov.kovapi.areas.game.entities.BattleSet;
import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.entities.Inventory;

public final class HeroFactory {

    public static Hero createNewHero(String heroName) {
        Hero hero = new Hero();
        hero.setName(heroName);
        hero.setBattleSet(new BattleSet());
        hero.setInventory(new Inventory());
        return hero;
    }

}
