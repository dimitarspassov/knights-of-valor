package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.HeroViewModel;
import com.dspassov.kovapi.areas.game.models.view.InventoryViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;

import java.util.List;

public interface HeroService {

    HeroServiceModel createNewHero(String heroName);

    HeroServiceModel findHeroByName(String heroName);

    HeroViewModel getCurrentHero();

    String buyItem(String itemId);

    InventoryViewModel getInventory();

    String sellItem(String itemId);

    String equipItem(String itemId);

    String unequipItem(String itemId);

    BattleSetViewModel getBattleSet();

}
