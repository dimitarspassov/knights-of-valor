package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.exceptions.InsufficientFundsException;
import com.dspassov.kovapi.exceptions.InventoryFullException;
import com.dspassov.kovapi.web.BaseController;
import com.dspassov.kovapi.web.ResponseMessageConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/game/hero")
public class HeroController extends BaseController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @GetMapping("")
    public String getHero() {
        try {
            return this.objectToJson(this.heroService.getCurrentHero());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/items/buy/{id}")
    public String buyItem(@PathVariable String id) {

        try {
            return this.success(this.heroService.buyItem(id));
        } catch (IllegalArgumentException | InsufficientFundsException | InventoryFullException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @GetMapping("/inventory")
    public String getInventory() {

        return this.objectToJson(this.heroService.getInventory());
    }

    @GetMapping("/battle-set")
    public String getBattleSet() {

        return this.objectToJson(this.heroService.getBattleSet());
    }

    @PostMapping("/items/sell/{itemId}")
    public String getInventory(@PathVariable String itemId) {

        try {
            return this.success(this.heroService.sellItem(itemId));
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/items/equip/{itemId}")
    public String equipItem(@PathVariable String itemId) {

        try {
            return this.success(this.heroService.equipItem(itemId));
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/items/unequip/{itemId}")
    public String unequipItem(@PathVariable String itemId) {

        try {
            return this.success(this.heroService.unequipItem(itemId));
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }
}

