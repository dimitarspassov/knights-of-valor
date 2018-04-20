package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.models.service.HeroCombatServiceModel;
import com.dspassov.kovapi.areas.game.models.service.HeroServiceModel;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.HeroViewModel;
import com.dspassov.kovapi.areas.game.models.view.InventoryViewModel;

import java.time.LocalDateTime;


public interface HeroService {

    HeroServiceModel createNewHero(String heroName);

    HeroServiceModel findHeroByName(String heroName);

    HeroViewModel getCurrentHero();

    HeroServiceModel getCurrentHeroServiceModel();

    String buyItem(String itemId);

    InventoryViewModel getInventory();

    String sellItem(String itemId);

    String equipItem(String itemId);

    String unequipItem(String itemId);

    BattleSetViewModel getBattleSet();

    void payHeroSalary(Long salary);

    void getLootFromHero(Long loot);

    void setTimeOfLastFight();

    void addExperiencePoints(Long experience);

    boolean isHeroReadyToFight();

    HeroCombatServiceModel getHeroCombatModel();
}
