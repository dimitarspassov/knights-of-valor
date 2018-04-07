package com.dspassov.kovapi.areas.game.services;


import com.dspassov.kovapi.areas.game.models.HeroServiceModel;

public interface HeroService {

     HeroServiceModel createNewHero(String heroName);

     HeroServiceModel findHeroByName(String heroName);
}
