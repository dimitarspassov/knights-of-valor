package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.common.HeroFactory;
import com.dspassov.kovapi.areas.game.entities.Hero;
import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.repositories.HeroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public HeroServiceModel createNewHero(String heroName) {
        Hero hero = HeroFactory.createNewHero(heroName);
        this.heroRepository.save(hero);
        return this.modelMapper.map(hero, HeroServiceModel.class);
    }

    @Override
    public HeroServiceModel findHeroByName(String heroName) {

        Hero hero = this.heroRepository.findByName(heroName);

        if (hero != null) {
            return this.modelMapper.map(hero, HeroServiceModel.class);
        }

        return null;
    }
}
