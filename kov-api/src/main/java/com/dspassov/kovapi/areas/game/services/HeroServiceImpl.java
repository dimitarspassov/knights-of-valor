package com.dspassov.kovapi.areas.game.services;

import com.dspassov.kovapi.areas.game.common.HeroFactory;
import com.dspassov.kovapi.areas.game.common.ItemFactory;
import com.dspassov.kovapi.areas.game.entities.*;
import com.dspassov.kovapi.areas.game.enumerations.ItemType;
import com.dspassov.kovapi.areas.game.models.HeroServiceModel;
import com.dspassov.kovapi.areas.game.models.view.BattleSetViewModel;
import com.dspassov.kovapi.areas.game.models.view.HeroViewModel;
import com.dspassov.kovapi.areas.game.models.view.InventoryViewModel;
import com.dspassov.kovapi.areas.game.models.view.ItemViewModel;
import com.dspassov.kovapi.exceptions.InsufficientFundsException;
import com.dspassov.kovapi.repositories.HeroRepository;
import com.dspassov.kovapi.security.SecurityService;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;
    private final ModelMapper modelMapper;
    private final SecurityService securityService;
    private final ItemService itemService;
    private final GameCalculationsService calculationsService;
    private final InventoryItemService inventoryItemService;

    @Autowired
    public HeroServiceImpl(HeroRepository heroRepository, ModelMapper modelMapper, SecurityService securityService, ItemService itemService, GameCalculationsService calculationsService, InventoryItemService inventoryItemService) {
        this.heroRepository = heroRepository;
        this.modelMapper = modelMapper;
        this.securityService = securityService;
        this.itemService = itemService;
        this.calculationsService = calculationsService;
        this.inventoryItemService = inventoryItemService;
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
        return this.modelMapper.map(this.getHeroOfCurrentUser(), HeroViewModel.class);
    }

    @Override
    public InventoryViewModel getInventory() {

        Hero hero = this.getHeroOfCurrentUser();
        List<ItemViewModel> items = new ArrayList<>();
        List<InventoryItem> inventoryItems = hero.getInventory().getItems();

        for (InventoryItem inventoryItem : inventoryItems) {
            for (int i = 0; i < inventoryItem.getCount(); i++) {
                items.add(this.modelMapper.map(inventoryItem.getItem(), ItemViewModel.class));
            }
        }

        InventoryViewModel model = new InventoryViewModel();
        model.setItems(items);
        model.setSize(hero.getInventory().getSize());
        return model;
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


    @Override
    public String sellItem(String itemId) {

        Hero hero = this.getHeroOfCurrentUser();
        List<InventoryItem> currentItems = hero.getInventory().getItems();
        List<InventoryItem> refreshedItems = new ArrayList<>();
        boolean sold = false;
        boolean isLast = false;
        String removedItemId = "";

        for (InventoryItem inventoryItem : currentItems) {

            if (!sold && itemId.equals(inventoryItem.getItem().getId()) && inventoryItem.getCount() > 0) {
                inventoryItem.decrementCount();
                int itemPrice = this.calculationsService.getInventoryItemPrice(inventoryItem.getItem().getPrice());
                hero.addGold((long) itemPrice);
                sold = true;
            }

            if (inventoryItem.getCount() > 0) {
                refreshedItems.add(inventoryItem);
            } else {
                isLast = true;
                removedItemId = inventoryItem.getId();

            }
        }

        hero.getInventory().setItems(refreshedItems);
        this.heroRepository.save(hero);

        if (sold && isLast) {
            this.inventoryItemService.deleteInventoryItemById(removedItemId);
        }
        return ResponseMessageConstants.ITEM_SOLD;
    }

    @Override
    public String equipItem(String itemId) {
        ItemViewModel itemModel = this.itemService.getItemById(itemId);

        Hero hero = this.getHeroOfCurrentUser();

        if (itemModel.getType().equals(ItemType.Weapon.name())) {
            hero.getBattleSet().setWeapon(this.modelMapper.map(itemModel, Weapon.class));
        } else if (itemModel.getType().equals(ItemType.Armor.name())) {
            hero.getBattleSet().setArmor(this.modelMapper.map(itemModel, Armor.class));
        } else {
            hero.getBattleSet().setShield(this.modelMapper.map(itemModel, Shield.class));
        }

        this.heroRepository.save(hero);
        return ResponseMessageConstants.ITEM_EQUIPPED;
    }

    @Override
    public String unequipItem(String itemId) {
        ItemViewModel itemModel = this.itemService.getItemById(itemId);

        Hero hero = this.getHeroOfCurrentUser();

        if (itemModel.getType().equals(ItemType.Weapon.name())) {
            hero.getBattleSet().setWeapon(null);
        } else if (itemModel.getType().equals(ItemType.Armor.name())) {
            hero.getBattleSet().setArmor(null);
        } else {
            hero.getBattleSet().setShield(null);
        }

        this.heroRepository.save(hero);
        return ResponseMessageConstants.ITEM_UNEQUIPPED;
    }

    @Override
    public BattleSetViewModel getBattleSet() {

        BattleSet battleSet = this.getHeroOfCurrentUser().getBattleSet();
        BattleSetViewModel battleSetViewModel = new BattleSetViewModel();
        if (battleSet.getWeapon() != null) {
            battleSetViewModel.setWeapon(this.modelMapper.map(battleSet.getWeapon(), ItemViewModel.class));
        }

        if (battleSet.getArmor() != null) {
            battleSetViewModel.setArmor(this.modelMapper.map(battleSet.getArmor(), ItemViewModel.class));
        }

        if (battleSet.getShield() != null) {
            battleSetViewModel.setShield(this.modelMapper.map(battleSet.getShield(), ItemViewModel.class));
        }


        return battleSetViewModel;
    }

    private Hero getHeroOfCurrentUser() {
        String currentUser = this.securityService.getCurrentPrincipal();
        return this.heroRepository.findByUser(currentUser);
    }
}
