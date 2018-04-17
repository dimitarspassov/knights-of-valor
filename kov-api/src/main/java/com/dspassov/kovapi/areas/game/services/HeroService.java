package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.models.view.HeroViewModel;

public interface HeroService {

    HeroServiceModel createNewHero(String heroName);

    HeroServiceModel findHeroByName(String heroName);

    HeroViewModel getCurrentHero();

    String buyItem(String itemId);
}
