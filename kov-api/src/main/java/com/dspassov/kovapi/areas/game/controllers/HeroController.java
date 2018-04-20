package com.dspassov.kovapi.areas.game.controllers;

import com.dspassov.kovapi.areas.game.services.CombatService;
import com.dspassov.kovapi.areas.game.services.HeroJobService;
import com.dspassov.kovapi.areas.game.services.HeroService;
import com.dspassov.kovapi.exceptions.HeroFightException;
import com.dspassov.kovapi.exceptions.HeroWorkException;
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
    private final HeroJobService heroJobService;
    private final CombatService combatService;

    @Autowired
    public HeroController(HeroService heroService, HeroJobService heroJobService, CombatService combatService) {
        this.heroService = heroService;
        this.heroJobService = heroJobService;
        this.combatService = combatService;
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

    @GetMapping("/arena")
    public String getArenaHeroes() {
        try {
            return this.objectToJson(this.heroService.getHeroesForArena());
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
    public String sellItem(@PathVariable String itemId) {

        try {
            return this.success(this.heroService.sellItem(itemId));
        } catch (IllegalArgumentException ex) {
            return this.error(ex.getMessage());
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

    @PostMapping("/job/start/{jobId}")
    public String startJob(@PathVariable String jobId) {

        try {
            return this.success(this.heroJobService.startJob(jobId));
        } catch (HeroWorkException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/job/abandon/{jobId}")
    public String abandonJob(@PathVariable String jobId) {

        try {
            return this.success(this.heroJobService.abandonJob(jobId));
        } catch (HeroWorkException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/job/finish/{jobId}")
    public String finishJob(@PathVariable String jobId) {

        try {
            return this.success(this.heroJobService.finishJob(jobId));
        } catch (HeroWorkException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @GetMapping("/job")
    public String isHeroAtWork() {
        return this.objectToJson(this.heroJobService.getCurrentJob());
    }

    @PostMapping("/neutrals/fight/{unitId}")
    public String fightNeutral(@PathVariable String unitId) {

        try {
            return this.success(this.combatService.heroFightWithNeutral(unitId));
        } catch (HeroWorkException | HeroFightException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }

    @PostMapping("/arena/fight/{heroId}")
    public String fightHero(@PathVariable String heroId) {

        try {
            return this.success(this.combatService.heroFightOnArena(heroId));
        } catch (HeroWorkException | HeroFightException ex) {
            return this.error(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return this.error(ResponseMessageConstants.GENERIC_ERROR);
        }
    }
}

