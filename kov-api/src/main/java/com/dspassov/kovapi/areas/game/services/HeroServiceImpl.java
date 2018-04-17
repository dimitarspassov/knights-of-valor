package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.common.HeroFactory;
import com.dspassov.kovapi.areas.game.common.ItemFactory;
import com.dspassov.kovapi.areas.game.entities.*;
import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.HeroViewModel;
import com.dspassov.kovapi.areas.game.models.view.InventoryViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.areas.users.services.UserService;
import com.dspassov.kovapi.exceptions.InsufficientFundsException;
import com.dspassov.kovapi.exceptions.InventoryFullException;
import com.dspassov.kovapi.repositories.HeroRepository;
import com.dspassov.kovapi.security.SecurityService;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.RectangularShape;
import java.util.HashSet;
import java.util.Set;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;
    private final ItemService itemService;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper, SecurityService securityService, ItemService itemService) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
        this.itemService = itemService;
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

    @Override
    public HeroViewModel getCurrentHero() {

        String currentUser = this.securityService.getCurrentPrincipal();
        Hero targetHero = this.heroRepository.findByUser(currentUser);

        return this.modelMapper.map(targetHero, HeroViewModel.class);
    }


    @Override
    public String buyItem(String itemId) {

        String currentUser = this.securityService.getCurrentPrincipal();
        Hero targetHero = this.heroRepository.findByUser(currentUser);

        ItemViewModel item = this.itemService.getItemById(itemId);

        if (targetHero.getGold() < item.getPrice()) {
            throw new InsufficientFundsException();
        }

        targetHero.setGold(targetHero.getGold() - item.getPrice());
        Item itemEntity;

        switch (item.getType()) {
            case "Weapon":
                itemEntity = this.modelMapper.map(item, Weapon.class);
                break;
            case "Armor":
                itemEntity = this.modelMapper.map(item, Armor.class);
                break;
            case "Shield":
                itemEntity = this.modelMapper.map(item, Shield.class);
                break;
            default:
                throw new IllegalArgumentException(ResponseMessageConstants.UNSUPPORTED_ITEM_TYPE);
        }


        targetHero.getInventory().addItem(ItemFactory.createNewInventoryItem(itemEntity));
        this.heroRepository.save(targetHero);

        return ResponseMessageConstants.ITEM_BOUGHT;
    }

}
